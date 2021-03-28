package estate.dto;


import java.math.BigDecimal;

public class TaxDto {

    private BigDecimal totalTax;

    public TaxDto() {
    }

    public TaxDto(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }
}
