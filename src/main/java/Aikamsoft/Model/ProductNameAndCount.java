package Aikamsoft.Model;

public class ProductNameAndCount {
    String paramNameProduct;
    String name;
    String paramCount;
    String count;

    public ProductNameAndCount(String paramNameProduct, String name, String paramCount, String count) {
        this.paramNameProduct = paramNameProduct;
        this.paramCount = paramCount;
        this.name = name;
        this.count = count;
    }

    public String getParamNameProduct() {
        return paramNameProduct;
    }

    public void setParamNameProduct(String paramNameProduct) {
        this.paramNameProduct = paramNameProduct;
    }

    public String getParamCount() {
        return paramCount;
    }

    public void setParamCount(String paramCount) {
        this.paramCount = paramCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}