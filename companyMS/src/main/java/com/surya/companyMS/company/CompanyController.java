package com.surya.companyMS.company;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<String> createCompany(@RequestBody Company company){
      String result =  companyService.createCompany(company);
      if(result==""){
          return new ResponseEntity<>("Company not created. You might have entered wrong input.", HttpStatus.NOT_FOUND);
      }
      return  new ResponseEntity<>(result,HttpStatus.CREATED);
    }


    @GetMapping
    public  ResponseEntity<List<Company>> getAllCompany(){
        List<Company> allComp = companyService.getAllCompany();

        if (allComp.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allComp,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> findCompanyById(@PathVariable Long id){
        Company comp = companyService.getCompanyById(id);
        if(comp!=null){
            return new ResponseEntity<>(comp,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable Long id){
        boolean res = companyService.deleteCompanyById(id);
        if(res){
            return new ResponseEntity<>("Company Deleted Successfully with id "+id,HttpStatus.OK);
        }
        return new ResponseEntity<>("Company Not Found with id "+id+" Please Enter valid company Id",HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompanyById(@PathVariable Long id, @RequestBody Company company){
        boolean res = companyService.updateCompany(id,company);
        if(res){
            return new ResponseEntity<>("Company Updated Successfully with id "+id,HttpStatus.OK);
        }
        return new ResponseEntity<>("Company Not Found with id "+id+" Please Enter valid company Id",HttpStatus.OK);
    }

//    @GetMapping("/jobs/{id}")
//    public ResponseEntity<List<Job>> getAllJobs(@PathVariable Long id){
//        List<Job> allJobs = companyService.getAllJobs(id);
//        if(allJobs.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(allJobs,HttpStatus.OK);
//    }
}
