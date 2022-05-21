package com.luca.lettore;

import com.luca.entities.Genere;
import com.luca.entities.Libro;
import com.luca.repository.DbGenereDao;
import com.luca.repository.DbLibroDao;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

public class LettoreC{
   //private final OPCPackage pkg=OPCPackage.open(new File("C:/Users/lucac/OneDrive/Desktop/Biblioteca.xlsx"));
   //private final XSSFWorkbook workbook= new XSSFWorkbook(pkg);
    private  Workbook workbook;
    private final DbGenereDao dbGenereDao=new DbGenereDao();
    private final DbLibroDao dbLibroDao=new DbLibroDao();
   public LettoreC(File file) throws InvalidFormatException, IOException {
       workbook=WorkbookFactory.create(file);
   }

   public void leggiPag() {
       for (Sheet sheet: workbook){
           System.out.println(sheet.getSheetName());
       }
   }

   public String estraiLettera(Sheet sheet){
       String ret="";
       DataFormatter dataFormatter=new DataFormatter();
       if(sheet.getRow(0).getCell(3)!=null){
           String codice=dataFormatter.formatCellValue(sheet.getRow(0).getCell(3));
           StringTokenizer st=new StringTokenizer(codice);
           ret=st.nextToken();
       }
       return ret;
   }

   public void carica() throws Exception {
       DataFormatter formatter=new DataFormatter();
       for(Sheet sheet: workbook){
           String lettera=estraiLettera(sheet);
          Genere genere=new Genere(sheet.getSheetName(), lettera);
          dbGenereDao.add(genere);
          int codgen=dbGenereDao.getCodiceByName(genere.getNome());
          for(Row row:sheet){
              Libro libro=new Libro();
              for(Cell cell:row){
                  int indice=cell.getColumnIndex();
                  switch(indice){
                      case 0: libro.setTitolo(formatter.formatCellValue(cell)); break;
                      case 1: libro.setAutore(formatter.formatCellValue(cell)); break;
                      case 2: libro.setCasa_edi(formatter.formatCellValue(cell));break;
                      case 3: if(cell.getStringCellValue()!=null){libro.setCodLib(formatter.formatCellValue(cell));} break;
                      default: break;
                  }
              }
              libro.setGenere(codgen);
              dbLibroDao.add(libro);
          }
          System.out.println("Ho inserito "+ sheet.getSheetName());
       }
   }



   public static void main(String...args) throws Exception {
       //LettoreC lettore=new LettoreC();
       //lettore.carica();
   }
}
