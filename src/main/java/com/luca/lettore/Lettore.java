package com.luca.lettore;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;

public interface Lettore {
    public File apriFile() throws IOException;
    public XSSFWorkbook openWorkbook();

}
