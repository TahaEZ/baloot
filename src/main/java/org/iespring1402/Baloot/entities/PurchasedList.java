// package org.iespring1402.Baloot.entities;

// import java.util.ArrayList;


// import jakarta.persistence.CascadeType;
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinTable;
// import jakarta.persistence.ManyToMany;
// import jakarta.persistence.Table;
// import jakarta.persistence.JoinColumn;

// @Entity
// @Table(name = "purchased_list")
// public class PurchasedList {

//     @Id
//     @Column(name = "id")
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private long id;

//     @ManyToMany(cascade = CascadeType.ALL)
//     @JoinTable(name = "purchased", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "discount_code_id"))
//     private ArrayList<CommodityDTO> purchasedItems = new ArrayList<>();

//     public ArrayList<CommodityDTO> getPurchasedItems() {
//         return purchasedItems;
//     }

//     public void setPurchasedItems(ArrayList<CommodityDTO> purchasedItems) {
//         this.purchasedItems = purchasedItems;
//     }
// }