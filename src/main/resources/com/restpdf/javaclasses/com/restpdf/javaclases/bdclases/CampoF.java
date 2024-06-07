package com.restpdf.javaclases.bdclases;

public class CampoF {
    String NameField, NameFatherForm;
    int Page, PosX, PosY, Height, Width, indexField;

    public CampoF() {
    }

    public String getNameField() {        return NameField;    }
    public void setNameField(String nameField) {        NameField = nameField;    }
    public String getNameFatherForm() {        return NameFatherForm;    }
    public void setNameFatherForm(String nameFatherForm) {        NameFatherForm = nameFatherForm;    }
    public int getPage() {        return Page;    }
    public void setPage(int page) {        Page = page;    }
    public int getPosX() {        return PosX;    }
    public void setPosX(int posX) {        PosX = posX;    }
    public int getPosY() {        return PosY;    }
    public void setPosY(int posY) {        PosY = posY;    }
    public int getHeight() {        return Height;    }
    public void setHeight(int height) {        Height = height;    }
    public int getWidth() {        return Width;    }
    public void setWidth(int width) {        Width = width;    }

    public int getIndexField() {
        return indexField;
    }

    public void setIndexField(int indexField) {
        this.indexField = indexField;
    }
    public CampoF(String nameField, String nameFatherForm, int page, int posX, int posY, int height, int weight) {
        NameField = nameField;
        NameFatherForm = nameFatherForm;
        Page = page;
        PosX = posX;
        PosY = posY;
        Height = height;
        Width = weight;
        indexField = -1;
    }
    public CampoF(String nameField, String nameFatherForm, int page, int posX, int posY, int height, int weight, int idf) {
        NameField = nameField;
        NameFatherForm = nameFatherForm;
        Page = page;
        PosX = posX;
        PosY = posY;
        Height = height;
        Width = weight;
        indexField = idf;
    }
    public CampoF(CampoF c){
        NameField = c.getNameField();
        NameFatherForm = c.getNameFatherForm();
        Page = c.getPage();
        PosX = c.getPosX();
        PosY = c.getPosY();
        Height = c.getHeight();
        Width = c.getWidth();

    }

    @Override
    public String toString() {
        return "CampoF{" +
                "NameField='" + NameField + '\'' +
                ", NameFatherForm='" + NameFatherForm + '\'' +
                ", Page='" + Page + '\'' +
                ", PosX=" + PosX +
                ", PosY=" + PosY +
                ", Height=" + Height +
                ", Width=" + Width +
                '}';
    }
}
