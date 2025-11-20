package com.example.companyserver.controller;

import com.example.companyserver.client.FinnhubClient;
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
    private final FinnhubClient finnhubClient;

    @GetMapping("/companies")
    public List<CompanyDto> getAllCompaniesMic() {
        return companyService.getFinnhubCompanies();
    }

    @PostMapping("/save/companies")
    public ResponseEntity<String> saveAllCompanies() {
        finnhubClient.saveAllCompanies();
        companyService.saveCompanies(companyMapper.dtoToCompanies(companyService.getDatabaseCompanies()));
        return new ResponseEntity<>("All companies were successfully saved", HttpStatus.OK);
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
