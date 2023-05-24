package com.example.individualpr.Controllers;

import com.example.individualpr.Models.Documents;
import com.example.individualpr.Models.User;
import com.example.individualpr.Repos.DocumentsRepos;
import com.example.individualpr.Repos.UserRepos;
import com.example.individualpr.RoleChek;
import com.example.individualpr.Service.DocumentImageService;
import com.lowagie.text.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

@Controller
@RequestMapping("/document")
public class DocumentsController {
    @Autowired
    private DocumentsRepos documentsRepos;
    @Autowired
    private UserRepos userRepository;

    private final DocumentImageService documentImageService;

    private static final String secretKey = "9kFBU/aD5G7QBmg3C9uhqw=="; // ключ шифрования

    public DocumentsController(DocumentImageService documentImageService) {
        this.documentImageService = documentImageService;
    }

    public static void main(String[] args) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // размер ключа
        SecretKey secretKey = keyGenerator.generateKey();
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Your secret key: " + encodedKey);
    }

    public static String encryptPassportNumber(String passportNumber) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(passportNumber.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptPassportNumber(String encryptedPassportNumber) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassportNumber);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    @GetMapping
    public String userList(Documents documents, Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            model.addAttribute("AuthUser",((UserDetails)principal).getUsername());
        } else {
            model.addAttribute("NotAuth", principal.toString());
        }
        model.addAttribute("documents", documentsRepos.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        model.addAttribute("user", userRepository.findAll());

//        String serPass = documents.getSerialPass();
//        String numPass = documents.getNumPass();
//        try {
//            decryptPassportNumber(serPass);
//            decryptPassportNumber(numPass);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        model.addAttribute("encrypSerPass", serPass);
//        model.addAttribute("encrypNumPass", numPass);

        return "User/docMain";
    }

    @GetMapping("/add")
    public String userAdd(Documents documents, Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            model.addAttribute("AuthUser",((UserDetails) principal).getUsername());
        } else {
            model.addAttribute("NotAuth", principal.toString());
        }
        UserDetails principial = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userAuth", principial.getUsername());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        model.addAttribute("user", userRepository.findAll());

        return "User/docAdd";
    }

    @PostMapping("/add")
    public String docAdd(@ModelAttribute("documents")@Valid Documents documents,
                         BindingResult bindingResult,
                         @RequestParam("file") MultipartFile file, Model model) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        if (bindingResult.hasErrors()){
            model.addAttribute("user", userRepository.findAll());
            return "User/docAdd";
        }
        // Получаем текущую аутентификационную информацию
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Если пользователь аутентифицирован, то получаем его имя (username)
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // Ищем пользователя с таким именем (username) в БД
            User currentUser = userRepository.findByLogin(username);

            // Далее выполняем необходимые действия

            //Получаем текущего пользователя
            //Проверяем существование записи пользователя в БД
            if (documentsRepos.existsByUser(currentUser)) {
                //Если запись существует - возвращаем ошибку
                model.addAttribute("messageException", "Вы уже добавили свой документ!");
//                throw new Exception("Пользователь уже добавил информацию в БД!");
                return "User/docAdd";
            } else {
                //Если записи не существует - добавляем новую запись в БД
                documents.setUser(currentUser);
                documentImageService.saveImageAndDocuments(documents, file);
            }
        }
        return "redirect:/document";
    }

    @GetMapping("/edit/{documents}")
//    @PreAuthorize("hasAnyAuthority('USER')")
    public String userEdit(Documents documents, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            model.addAttribute("AuthUser",((UserDetails)principal).getUsername());
        } else {
            model.addAttribute("NotAuth", principal.toString());
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        return "User/docEdit";
    }

    @PostMapping("/edit/{documents}")
//    @PreAuthorize("hasAnyAuthority('USER')")
    public String userEditSave(
            @ModelAttribute("documents") @Valid Documents documents,
            @RequestParam("file") @Valid MultipartFile file,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userRepository.findAll());
            return "User/docEdit";
        }
        documentImageService.saveImageAndDocuments(documents, file);
        return "redirect:/document";
    }

    @GetMapping("/del/{documents}")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String userDel(
            Documents documents, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        documentsRepos.delete(documents);
        return "redirect:/document";
    }

    @GetMapping("/check/{documents}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    public String checkedDocument(
            Documents documents, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        documents.setChecked(Boolean.TRUE);
        documentsRepos.save(documents);
        return "redirect:/document";
    }

}