package com.example.companyserver.controller;

import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.mapper.CompanyMapper;
import com.example.companyserver.service.CompanyService;
import com.example.companyserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final CompanyMapper companyMapper;
    private final CompanyService companyService;
    private final UserService userService;

    @GetMapping("/companies")
    public List<CompanyDto> getAllCompanies() {
        return companyService.getCompanies();
    }

    @PostMapping("/save/companies")
    public ResponseEntity<String> saveAllCompanies() {
        companyService.saveCompanies(companyMapper.dtoToCompanies(companyService.getCompanies()));
        return new ResponseEntity<>("All companies were successfully saved", HttpStatus.OK);
    }

    @DeleteMapping("/delete/company/{symbol}")
    public ResponseEntity<String> deleteCompanyBySymbol(@PathVariable String symbol) {
        companyService.deleteCompany(symbol);
        return new ResponseEntity<>("Company with this symbol was successfully deleted" + symbol, HttpStatus.OK);
    }

    @DeleteMapping("/delete/company/all")
    public ResponseEntity<String> deleteAllCompanies() {
        companyService.deleteAllCompanies();
        return new ResponseEntity<>("All companies were successfully deleted", HttpStatus.OK);
    }

    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        userService.delete(id);
        return new ResponseEntity<>("User with this id was deleted: " + id, HttpStatus.OK);
    }

    @PostMapping("/block/{userId}")
    public ResponseEntity<String> blockUser(@PathVariable Long userId) {
        userService.blockUser(userId);
        return new ResponseEntity<>("User with this id was blocked: " + userId, HttpStatus.OK);
    }

    @PostMapping("/unblock/{userId}")
    public ResponseEntity<String> unblockUser(@PathVariable Long userId) {
        userService.unblockUser(userId);
        return new ResponseEntity<>("User with this id was unblocked: " + userId, HttpStatus.OK);
    }

}
