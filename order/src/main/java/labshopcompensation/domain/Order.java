package labshopcompensation.domain;

import labshopcompensation.domain.OrderPlaced;
import labshopcompensation.domain.OrderCancelled;
import labshopcompensation.OrderApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;
import java.time.LocalDate;


@Entity
@Table(name="Order_table")
@Data

//<<< DDD / Aggregate Root
public class Order  {


    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    private Long id;
    
    
    
    
    private String productId;
    
    
    
    
    private Integer qty;
    
    
    
    
    private String customerId;
    
    
    
    
    private Double amount;
    
    
    
    
    private String status;
    
    
    
    
    private String address;

    @PostPersist
    public void onPostPersist(){


        OrderPlaced orderPlaced = new OrderPlaced(this);
        orderPlaced.publishAfterCommit();

    
    }
    @PrePersist
    public void onPrePersist(){
    
    }
    @PreRemove
    public void onPreRemove(){


        OrderCancelled orderCancelled = new OrderCancelled(this);
        orderCancelled.publishAfterCommit();

    
    }

    public static OrderRepository repository(){
        OrderRepository orderRepository = OrderApplication.applicationContext.getBean(OrderRepository.class);
        return orderRepository;
    }



    public void order(){
        //implement business logic here:
        
        OrderPlaced orderPlaced = new OrderPlaced(this);
        orderPlaced.publishAfterCommit();
        
        
        labshopcompensation.external.InventoryQuery inventoryQuery = new labshopcompensation.external.InventoryQuery();
        OrderApplication.applicationContext
            .getBean(labshopcompensation.external.Service.class)
            .( inventoryQuery);
    }
    public void cancel(){
        //implement business logic here:
        
        
        
    }



}
//>>> DDD / Aggregate Root
