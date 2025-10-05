package gt.edu.umg.tiendadb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class TIENDADB {

    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gt.edu.umg_TIENDADB_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        
        try {
            System.out.println("✅ Conexión a la base de datos exitosa!");
            
            Scanner sString = new Scanner(System.in);
            Producto a = new Producto();
            
            System.out.println("Ingrese nombre del producto:");
            String nombre = sString.nextLine();
            a.setNombre(nombre);
            
            System.out.println("Ingrese precio de venta:");
            double precioVenta = sString.nextDouble();
            a.setPrecioVenta(BigDecimal.valueOf(precioVenta).setScale(2, RoundingMode.HALF_UP));
            
            System.out.println("Ingrese precio de compra:");
            double precioCompra = sString.nextDouble();
            a.setPrecioCompra(BigDecimal.valueOf(precioCompra).setScale(2, RoundingMode.HALF_UP));
            
            System.out.println("Ingrese stock:");
            int stock = sString.nextInt();
            a.setStock(stock);
            
            System.out.println("Ingrese IdCategoria:");
            int idCategoria = sString.nextInt();
            
            Categoria categoria = em.find(Categoria.class, idCategoria);
            if (categoria == null) {
                System.out.println("❌ Categoría con ID " + idCategoria + " no existe.");
                return;
            }
            a.setIdCategoria(categoria);
            
            // ✅ GUARDAR EN LA BASE DE DATOS
            em.getTransaction().begin();
            em.persist(a);
            em.getTransaction().commit();
            
            System.out.println("\n✅ Producto guardado exitosamente!");
            System.out.println("\n📌 Datos guardados:");
            System.out.println("- Nombre: " + a.getNombre());
            System.out.println("- Precio Venta: " + a.getPrecioVenta());
            System.out.println("- Precio Compra: " + a.getPrecioCompra());
            System.out.println("- Stock: " + a.getStock());
            System.out.println("- IdCategoria: " + a.getIdCategoria().getIdCategoria());
            
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("❌ Error al guardar:");
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) em.close();
            if (emf != null) emf.close();
        }
    }
}