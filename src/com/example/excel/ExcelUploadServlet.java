package com.example.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.sql.Types.BOOLEAN;
import static java.sql.Types.NUMERIC;
import static org.springmodules.validation.valang.parser.ValangParserConstants.STRING;


@Controller
@RequestMapping("/excel")
public class ExcelUploadServlet {

    @RequestMapping(value = "/upload.do", method = RequestMethod.POST)
    public String uploadExcel(@RequestParam("excelFile")MultipartFile file , ModelMap model){
        List<Map<String, String>> customerList = new ArrayList<>();
        if (!file.getOriginalFilename().endsWith(".xlsx")) {
            model.addAttribute("error", "엑셀(.xlsx) 파일만 업로드 가능합니다.");
            return "excel/excelResult";
        }
        try{
            InputStream is = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);

            Row headerRow0 = sheet.getRow(0);
            if (headerRow0 == null ||
                !getCellValue(headerRow0.getCell(0)).equals("고객명") ||
                !getCellValue(headerRow0.getCell(1)).equals("전화번호") ||
                !getCellValue(headerRow0.getCell(2)).equals("이메일") ||
                !getCellValue(headerRow0.getCell(3)).equals("가입일")) {
                model.addAttribute("error", "엑셀 형식이 올바르지 않습니다. 헤더를 확인해주세요.");
                return "excel/excelResult";
            }

            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Map<String, String> customer = new HashMap<>();
                customer.put("name", getCellValue(row.getCell(0)));
                customer.put("phone", getCellValue(row.getCell(1)));
                customer.put("email", getCellValue(row.getCell(2)));
                customer.put("joinDate", getCellValue(row.getCell(3)));
                customerList.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "엑셀 파일을 읽는 중 오류가 발생했습니다.");
        }
        model.addAttribute("customerList", customerList);


        return "excel/excelResult";

    }

    private String getCellValue(Cell cell) {

        String value = "";

        if(cell == null) {
            value = "";
        }else {
            if( cell.getCellType() == Cell.CELL_TYPE_FORMULA ) {
                value = cell.getCellFormula();
            }
            else if( cell.getCellType() == Cell.CELL_TYPE_NUMERIC ) {
                if(DateUtil.isCellDateFormatted(cell)){
                    value = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                    // value = cell.getNumericCellValue() + "";
                }else {
                    // 전화번호나 일반 숫자 → 소수점 없이 문자열로
                    double num = cell.getNumericCellValue();
                    long longVal = (long) num;
                    if (num == (double) longVal) {
                        value = String.valueOf(longVal);
                    } else {
                        value = String.valueOf(num);
                    }
                }

            }
            else if( cell.getCellType() == Cell.CELL_TYPE_STRING ) {
                value = cell.getStringCellValue();
            }
            else if( cell.getCellType() == Cell.CELL_TYPE_BOOLEAN ) {
                value = cell.getBooleanCellValue() + "";
            }
            else if( cell.getCellType() == Cell.CELL_TYPE_ERROR ) {
                value = cell.getErrorCellValue() + "";
            }
            else if( cell.getCellType() == Cell.CELL_TYPE_BLANK ) {
                value = "";

            }else if(String.valueOf(cell.getStringCellValue()).trim().length() <=0 )
            {
                value = "";
            }
            else {
                value = cell.getStringCellValue();
            }
        }
        return value;
    }


}


