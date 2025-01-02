import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Chanuka Prabodha
 * Date: 2024-12-18
 * Time: 11:32 AM
 * Description:
 */
@NoArgsConstructor
@AllArgsConstructor
@Data

public class CustomerDTO {
    private String id;
    private String name;
    private String address;
}
