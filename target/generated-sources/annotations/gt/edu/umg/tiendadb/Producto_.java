package gt.edu.umg.tiendadb;

import gt.edu.umg.tiendadb.Categoria;
import gt.edu.umg.tiendadb.DetalleCompra;
import gt.edu.umg.tiendadb.DetalleFactura;
import gt.edu.umg.tiendadb.DetalleVenta;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-10-04T08:39:32", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Producto.class)
public class Producto_ { 

    public static volatile CollectionAttribute<Producto, DetalleFactura> detalleFacturaCollection;
    public static volatile CollectionAttribute<Producto, DetalleCompra> detalleCompraCollection;
    public static volatile SingularAttribute<Producto, BigDecimal> precioCompra;
    public static volatile SingularAttribute<Producto, Integer> idProducto;
    public static volatile SingularAttribute<Producto, BigDecimal> precioVenta;
    public static volatile SingularAttribute<Producto, Integer> stock;
    public static volatile SingularAttribute<Producto, Categoria> idCategoria;
    public static volatile SingularAttribute<Producto, String> nombre;
    public static volatile CollectionAttribute<Producto, DetalleVenta> detalleVentaCollection;

}