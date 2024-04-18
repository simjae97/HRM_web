package sierp.springteam1.controller.salarycontroller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sierp.springteam1.model.dto.BoardPageDTO;
import sierp.springteam1.model.dto.EmployeeDto;
import sierp.springteam1.model.dto.SalaryDto;
import sierp.springteam1.service.salaryService.SalaryService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/salary")
public class Salarycontroller {
    @Autowired
    SalaryService salaryService;
//
//
    @GetMapping("/findlist.do")
    @ResponseBody
    public BoardPageDTO findSalayloglist(int page , int pageBoardSize , int state, String key, String keyword){
        System.out.println("컨트롤러");
        return salaryService.findSalayloglist(page,pageBoardSize,state,key,keyword);

    }
    @GetMapping("/test")
    @ResponseBody
    public void insertsal(){
        salaryService.insertsel();
    }

    @GetMapping("/del.do")
    @ResponseBody
    public boolean deleteSalary(int eno, double price , String smonth ){
        SalaryDto salaryDto =  SalaryDto.builder()
                .employeeDto(EmployeeDto.builder()
                        .eno(eno)
                        .build())
                .price(price)
                .smonth(smonth)
                .build();
        return salaryService.deleteSalary(salaryDto);
    }
    @GetMapping("/list.do")
    @ResponseBody
    public BoardPageDTO findSalarylist(int page , int pageBoardSize , int state, String key, String keyword){
        System.out.println("ddd");
        return salaryService.findSalarylist(page , pageBoardSize ,state, key, keyword);
    }

    @PostMapping("/insert.do")
    @ResponseBody
    public boolean salaryloginput(HttpServletRequest request){
//        return salaryService.salaryloginput(result);
//        for (SalaryDto salaryDto : result) {
//            System.out.println("salaryDto.getPrice() = " + salaryDto.getPrice());
//        }
        try {


            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            // JSON을 파싱하여 객체 배열로 변환
            String jsonData = sb.toString();
            System.out.println(jsonData);
            String[] jsonArray = jsonData.substring(1, jsonData.length() - 1).split(",");

            System.out.println(jsonArray.length);
            ArrayList<SalaryDto> result = new ArrayList<>();
            for (int i = 0; i < jsonArray.length; i += 3) {
                System.out.println("ddd"+jsonArray[i].split(":")[1]);
                System.out.println("dddddddddd"+jsonArray[i+1].split("\"")[3].split("\\\\")[0]);
                System.out.println("dddddddddd"+jsonArray[i+2].split(":")[1].split("}")[0]);

//                System.out.println(jsonArray[i].split(":")[1]);
//                System.out.println(jsonArray[i]);
//                System.out.println("jsonArray[i+1].split(\":\")[1].split(\"}\")[0]) = " + jsonArray[i+1].split(":")[1].split("}")[0]);
                result.add( SalaryDto.builder()
                        .employeeDto(EmployeeDto.builder()
                                .eno(Integer.parseInt(jsonArray[i].split(":")[1]))
                                .build())
                        .smonth(jsonArray[i+1].split("\"")[3].split("\\\\")[0])
                        .price(Double.parseDouble(jsonArray[i+2].split(":")[1].split("}")[0]))
                        .build()
                ); // YourObject 클래스에 따라 파싱을 변경해야 함
            }


//            // 받은 배열을 처리하거나 다른 작업 수행
//            for (SalaryDto obj : objects) {
//                System.out.println(obj.toString());
//            }
            return salaryService.salaryloginput(result);
        }
        catch (Exception e){
            System.out.println("json파싱도중 발생한 오류 = " + e);
        }
        return true;
    }

    @GetMapping("/findlist")
    public String salaryloglist(){

        return "/salary/salaryloglist";

    }

    @GetMapping("/list")
    public String Salarylist(){

        return "/salary/salarylist";
    }

}
