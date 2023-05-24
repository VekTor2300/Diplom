package com.example.individualpr.Controllers;

import com.example.individualpr.Config.DBConfig;
import com.example.individualpr.RoleChek;
import com.example.individualpr.Service.ConsoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("backup")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class DBController {

    @GetMapping
    public String getMainPage(Model model) {

        model.addAttribute("restoreFiles", getRestoreList());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        return "backup/main";
    }

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    ServletContext context;

    public List<String> getRestoreList() {//создание списка из папки дампс

        Path path = Paths.get("dumps");

        File folder = new File(path.toUri());

        List<String> dumps = new ArrayList<>();

        for(File dump: folder.listFiles()) {
            if(dump.isFile()) {
                dumps.add(dump.getName());
            }
        }
        Collections.reverse(dumps);
        return dumps;
    }

    @GetMapping("downloadSQL")
    @Deprecated
    public ResponseEntity<Resource> downloadDB(HttpServletResponse response, @RequestParam(value = "withData", required = false) Boolean withData, Model model) {
        //метод для скачивания скриптв
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        DBConfig.initField();
        String command = String.format("mysqldump --no-tablespaces -u%s -p%s -h%s %s > %s",//команды для cmd файла
                DBConfig.username, DBConfig.password, DBConfig.host, DBConfig.dbname, "load/" + DBConfig.dbname + ".sql");
        if(withData == null) {
            command += " --no-data";
        }
        try {
            ConsoleService.exec(command);
            String uri = Paths.get("load/" + DBConfig.dbname + ".sql").toUri().toString();
            Resource resource = resourceLoader.getResource(uri);
            System.out.println(uri);
            ResponseEntity<Resource> body = ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
                    .contentLength(resource.contentLength())
                    .body(resource);
            return body;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("createDump")
    @Deprecated
    public String createDump(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        DBConfig.initField();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_");
        String filename = sdf.format(new Date()) + "BACKUPshop_cosmetic.sql";
        String command = String.format("mysqldump -u%s -p%s -h%s --databases %s > %s",
                DBConfig.username, DBConfig.password, DBConfig.host, DBConfig.dbname, "dumps/" + filename);
        try {
            ConsoleService.exec(command);
            model.addAttribute("restoreFiles", getRestoreList());
            return "backup/main";
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("deleteDump")
    public String deleteDump(Model model, @RequestParam("filename") String filename) throws IOException {
        File file = new File(Paths.get("dumps/" + filename).toUri());

        Files.deleteIfExists(file.toPath());

        model.addAttribute("restoreFiles", getRestoreList());
        return "redirect:/backup";
    }

    @PostMapping("restore")
    @Deprecated
    public String restore(Model model, @RequestParam("fileName") String filename){
        restore(filename, "dumps", model);
        return "redirect:/backup";
    }

    private void restore(String filename, String folder,  Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));
        DBConfig.initField();
        File file = new File(Paths.get( folder + "/" + filename).toString());

        String command = String.format("mysql -u%s -p%s -h%s %s < %s",
                DBConfig.username, DBConfig.password, DBConfig.host, DBConfig.dbname , file.toPath());
        try {
            ConsoleService.exec(command);
            model.addAttribute("restoreFiles", getRestoreList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("upload")
    public String uploadRestore(Model model, @RequestParam("file") MultipartFile file) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isUser", new RoleChek().userCheck(auth));
        model.addAttribute("isAdmin", new RoleChek().adminCheck(auth));
        model.addAttribute("isEmployee", new RoleChek().employeeCheck(auth));

        Path path = Paths.get("load/" + "customDump.sql");

        File customDump = new File(path.toUri());

        FileOutputStream fos = new FileOutputStream(customDump);
        fos.write(file.getBytes());
        fos.close();

        restore("customDump.sql", "load", model);
        return "backup/main";
    }
}
