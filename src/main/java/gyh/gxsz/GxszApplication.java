package gyh.gxsz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("gyh.gxsz.mapper")
public class GxszApplication {

    public static void main(String[] args) {
        SpringApplication.run(GxszApplication.class, args);
    }

}
