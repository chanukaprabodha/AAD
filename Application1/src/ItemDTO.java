import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Chanuka Prabodha
 * Date: 2025-01-01
 * Time: 11:41 PM
 * Description:
 */
@NoArgsConstructor
@AllArgsConstructor
@Data

public class ItemDTO {
    private String code;
    private String name;
    private double price;
    private double qty;
}
