package org.javaboy.vhr.utils;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.javaboy.vhr.model.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/1/17 21:16
 */
public class HrUtils {
    public static Hr getCurrentHr(){
        return (Hr) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        //当前用户对象
    }

    /**
     * @author gcx_javaboy
     * @version 1.0
     * @PROJECT_NAME: vhr
     * @DATE: 2021/1/27 20:39
     */
    public static class POIUtils {
        public static ResponseEntity<byte[]> employee2Excel(List<Employee> list) {
            //1.创建一个Excel文档
            HSSFWorkbook workbook = new HSSFWorkbook();
            //2.创建文档摘要
            workbook.createInformationProperties();
            //3.获取并配置文档信息
            DocumentSummaryInformation docInfo = new DocumentSummaryInformation();
            //文档类别
            docInfo.setCategory("员工信息");
            //文档管理员
            docInfo.setManager("gcxjava");
            //设置公司
            docInfo.setCompany("www.gcx-java.com");
            //4.获取文档摘要信息
            SummaryInformation summaryInformation = workbook.getSummaryInformation();
            //文档标题
            summaryInformation.setTitle("员工信息表");
            //文档作者
            summaryInformation.setAuthor("gcx");
            //文档备注
            summaryInformation.setComments("本文档由gcx所有");
            //5.创建样式
            //创建 标题行的样式
            HSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillBackgroundColor(IndexedColors.YELLOW.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            HSSFCellStyle dataCellstyle = workbook.createCellStyle();
            dataCellstyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
            HSSFSheet sheet = workbook.createSheet("员工信息表");
            //设置列的宽度
            sheet.setColumnWidth(0, 5 * 256);
            sheet.setColumnWidth(1, 12 * 256);
            sheet.setColumnWidth(2, 10 * 256);
            sheet.setColumnWidth(3, 5 * 256);
            sheet.setColumnWidth(4, 12 * 256);
            sheet.setColumnWidth(5, 20 * 256);
            sheet.setColumnWidth(6, 10 * 256);
            sheet.setColumnWidth(7, 10 * 256);
            sheet.setColumnWidth(8, 16 * 256);
            sheet.setColumnWidth(9, 12 * 256);
            sheet.setColumnWidth(10, 15 * 256);
            sheet.setColumnWidth(11, 20 * 256);
            sheet.setColumnWidth(12, 16 * 256);
            sheet.setColumnWidth(13, 14 * 256);
            sheet.setColumnWidth(14, 14 * 256);
            sheet.setColumnWidth(15, 12 * 256);
            sheet.setColumnWidth(16, 8 * 256);
            sheet.setColumnWidth(17, 20 * 256);
            sheet.setColumnWidth(18, 20 * 256);
            sheet.setColumnWidth(19, 15 * 256);
            sheet.setColumnWidth(20, 8 * 256);
            sheet.setColumnWidth(21, 25 * 256);
            sheet.setColumnWidth(22, 14 * 256);
            sheet.setColumnWidth(23, 15 * 256);
            sheet.setColumnWidth(24, 15 * 256);
            sheet.setColumnWidth(25, 15 * 256);
            //6.创建标题行
            HSSFRow row0 = sheet.createRow(0);//创建行
            HSSFCell cell0 = row0.createCell(0);//创建列
            cell0.setCellValue("编号");
            cell0.setCellStyle(headerStyle);

            HSSFCell cell1 = row0.createCell(1);
            cell1.setCellValue("姓名");
            cell1.setCellStyle(headerStyle);

            HSSFCell cell2 = row0.createCell(2);
            cell2.setCellValue("工号");
            cell2.setCellStyle(headerStyle);

            HSSFCell cell3 = row0.createCell(3);
            cell3.setCellValue("性别");
            cell3.setCellStyle(headerStyle);

            HSSFCell cell4 = row0.createCell(4);
            cell4.setCellValue("出生日期");
            cell4.setCellStyle(headerStyle);

            HSSFCell cell5 = row0.createCell(5);
            cell5.setCellValue("身份证号码");
            cell5.setCellStyle(headerStyle);

            HSSFCell cell6 = row0.createCell(6);
            cell6.setCellValue("婚姻状况");
            cell6.setCellStyle(headerStyle);

            HSSFCell cell7 = row0.createCell(7);
            cell7.setCellValue("民族");
            cell7.setCellStyle(headerStyle);

            HSSFCell cell8 = row0.createCell(8);
            cell8.setCellValue("籍贯");
            cell8.setCellStyle(headerStyle);

            HSSFCell cell9 = row0.createCell(9);
            cell9.setCellValue("政治面貌");
            cell9.setCellStyle(headerStyle);

            HSSFCell cell10 = row0.createCell(10);
            cell10.setCellStyle(headerStyle);
            cell10.setCellValue("电话号码");

            HSSFCell cell11 = row0.createCell(11);
            cell11.setCellStyle(headerStyle);
            cell11.setCellValue("联系地址");

            HSSFCell cell12 = row0.createCell(12);
            cell12.setCellStyle(headerStyle);
            cell12.setCellValue("所属部门");

            HSSFCell cell13 = row0.createCell(13);
            cell13.setCellStyle(headerStyle);
            cell13.setCellValue("职称");

            HSSFCell cell14 = row0.createCell(14);
            cell14.setCellStyle(headerStyle);
            cell14.setCellValue("职位");

            HSSFCell cell15 = row0.createCell(15);
            cell15.setCellStyle(headerStyle);
            cell15.setCellValue("聘用形式");

            HSSFCell cell16 = row0.createCell(16);
            cell16.setCellStyle(headerStyle);
            cell16.setCellValue("最高学历");

            HSSFCell cell17 = row0.createCell(17);
            cell17.setCellStyle(headerStyle);
            cell17.setCellValue("专业");

            HSSFCell cell18 = row0.createCell(18);
            cell18.setCellStyle(headerStyle);
            cell18.setCellValue("毕业院校");

            HSSFCell cell19 = row0.createCell(19);
            cell19.setCellStyle(headerStyle);
            cell19.setCellValue("入职日期");

            HSSFCell cell20 = row0.createCell(20);
            cell20.setCellStyle(headerStyle);
            cell20.setCellValue("在职状态");

            HSSFCell cell21 = row0.createCell(21);
            cell21.setCellStyle(headerStyle);
            cell21.setCellValue("邮箱");

            HSSFCell cell22 = row0.createCell(22);
            cell22.setCellStyle(headerStyle);
            cell22.setCellValue("合同期限（年）");

            HSSFCell cell23 = row0.createCell(23);
            cell23.setCellStyle(headerStyle);
            cell23.setCellValue("合同起始日期");

            HSSFCell cell24 = row0.createCell(24);
            cell24.setCellStyle(headerStyle);
            cell24.setCellValue("合同终止日期");

            HSSFCell cell25 = row0.createCell(25);
            cell25.setCellStyle(headerStyle);
            cell25.setCellValue("转正日期");
            for (int i = 0; i < list.size(); i++) {
                Employee emp = list.get(i);
                HSSFRow row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(emp.getId());
                row.createCell(1).setCellValue(emp.getName());
                row.createCell(2).setCellValue(emp.getWorkID());
                row.createCell(3).setCellValue(emp.getGender());
                HSSFCell c4 = row.createCell(4);
                c4.setCellStyle(dataCellstyle);
                c4.setCellValue(emp.getBirthday());
                row.createCell(5).setCellValue(emp.getIdCard());
                row.createCell(6).setCellValue(emp.getWedlock());
                row.createCell(7).setCellValue(emp.getNation().getName());
                row.createCell(8).setCellValue(emp.getNativePlace());
                row.createCell(9).setCellValue(emp.getPoliticsstatus().getName());
                row.createCell(10).setCellValue(emp.getPhone());
                row.createCell(11).setCellValue(emp.getAddress());
                row.createCell(12).setCellValue(emp.getDepartment().getName());
                row.createCell(13).setCellValue(emp.getJobLevel().getName());
                row.createCell(14).setCellValue(emp.getPosition().getName());
                row.createCell(15).setCellValue(emp.getEngageForm());
                row.createCell(16).setCellValue(emp.getTiptopDegree());
                row.createCell(17).setCellValue(emp.getSpecialty());
                row.createCell(18).setCellValue(emp.getSchool());
                HSSFCell c19 = row.createCell(19);
                c19.setCellStyle(dataCellstyle);
                c19.setCellValue(emp.getBeginDate());
                row.createCell(20).setCellValue(emp.getWorkState());
                row.createCell(21).setCellValue(emp.getEmail());
                row.createCell(22).setCellValue(emp.getContractTerm());
                HSSFCell c23 = row.createCell(23);
                c23.setCellStyle(dataCellstyle);
                c23.setCellValue(emp.getBeginContract());
                HSSFCell c24 = row.createCell(24);
                c24.setCellStyle(dataCellstyle);
                c24.setCellValue(emp.getEndContract());
                HSSFCell c25 = row.createCell(25);
                c25.setCellStyle(dataCellstyle);
                c25.setCellValue(emp.getConversionTime());
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            HttpHeaders headers = new HttpHeaders();
            try {
                headers.setContentDispositionFormData("attachment", new String("员工表.xls".getBytes("UTF-8"), "ISO-8859-1"));
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                workbook.write(baos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
        }

        /**
         * excel解析成员工集合
         *
         * @param file
         * @param allNations
         * @param allPoliticstatus
         * @param allDepartments
         * @param allPositions
         * @param allJobLevels
         * @return
         */
        public static List<Employee> Excel2employee(MultipartFile file, List<Nation> allNations, List<Politicsstatus> allPoliticstatus, List<Department> allDepartments, List<Position> allPositions, List<JobLevel> allJobLevels) {
            List<Employee> list = new ArrayList<>();
            Employee employee = null;//声明一个employee
            try {
                //1.创建一个workbook对象
                HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());//新建一个excel
                //2.获取workbook表单的数量
                int numberOfSheet = workbook.getNumberOfSheets();
                for (int i = 0; i < numberOfSheet; i++) {
                    //3.循环获取单个表单
                    HSSFSheet sheet = workbook.getSheetAt(i);
                    //4.获取表单中的行数
                    int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                    for (int j = 0; j < physicalNumberOfRows; j++) {
                        //5.跳过标题行
                        if (j == 0) {
                            continue;//跳过标题行
                        }
                        //6.获取行
                        HSSFRow row = sheet.getRow(j);
                        if (row == null) {
                            continue;//防止数据中间有空行
                        }
                        //7.获取每一行的列数 然后遍历
                        int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                        employee = new Employee();
                        for (int k = 0; k < physicalNumberOfCells; k++) {
                            HSSFCell cell = row.getCell(k);
                            switch (cell.getCellType()) {
                                case STRING: {
                                    String cellValue = cell.getStringCellValue();
                                    switch (k) {
                                        case 1:
                                            employee.setName(cellValue);
                                            break;
                                        case 2:
                                            employee.setWorkID(cellValue);
                                            break;
                                        case 3:
                                            employee.setGender(cellValue);
                                            break;
                                        case 5:
                                            employee.setIdCard(cellValue);
                                            break;
                                        case 6:
                                            employee.setWedlock(cellValue);
                                            break;
                                        case 7:
                                            int i1 = allNations.indexOf(new Nation(cellValue));
                                            employee.setNationId(allNations.get(i1).getId());//利用name一致就认为是相同元素，这样找到index 然后再找到对应的部门id
                                            break;
                                        case 8:
                                            employee.setNativePlace(cellValue);
                                            break;
                                        case 9: {
                                            int i2 = allPoliticstatus.indexOf(new Politicsstatus(cellValue));
                                            employee.setPoliticId(allPoliticstatus.get(i2).getId());
                                            break;
                                        }
                                        case 10:
                                            employee.setPhone(cellValue);
                                            break;
                                        case 11:
                                            employee.setAddress(cellValue);
                                            break;
                                        case 12:
                                            int i3 = allDepartments.indexOf(new Department(cellValue));
                                            employee.setDepartmentId(allDepartments.get(i3).getId());
                                            break;
                                        case 13:
                                            int i4 = allJobLevels.indexOf(new JobLevel(cellValue));
                                            employee.setJobLevelId(allJobLevels.get(i4).getId());
                                            break;
                                        case 14:
                                            int i5 = allPositions.indexOf(new Position(cellValue));
                                            employee.setPosId(allPositions.get(i5).getId());
                                            break;
                                        case 15:
                                            employee.setEngageForm(cellValue);
                                            break;
                                        case 16:
                                            employee.setTiptopDegree(cellValue);
                                            break;
                                        case 17:
                                            employee.setSpecialty(cellValue);
                                            break;
                                        case 18:
                                            employee.setSchool(cellValue);
                                            break;
                                        case 20:
                                            employee.setWorkState(cellValue);
                                            break;
                                        case 21: {
                                            employee.setEmail(cellValue);
                                            break;
                                        }
                                    }
                                    break;
                                }
                                default: {//不是string
                                    switch (k) {
                                        case 4: {
                                            employee.setBirthday(cell.getDateCellValue());
                                            break;
                                        }
                                        case 19:
                                            employee.setBeginDate(cell.getDateCellValue());
                                            break;
                                        case 23:
                                            employee.setBeginContract(cell.getDateCellValue());
                                            break;
                                        case 24:
                                            employee.setEndContract(cell.getDateCellValue());
                                            break;
                                        case 25:
                                            employee.setConversionTime(cell.getDateCellValue());
                                            break;
                                        case 22:
                                            employee.setContractTerm(cell.getNumericCellValue());
                                            break;
                                    }
                                    break;
                                }
                            }

                        }
                        list.add(employee);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return list;
        }
    }
}
