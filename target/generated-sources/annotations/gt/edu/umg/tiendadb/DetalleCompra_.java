package gt.edu.umg.tiendadb;

import gt.edu.umg.tiendadb.Compra;
import gt.edu.umg.tiendadb.Producto;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-10-04T08:39:32", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(DetalleCompra.class)
public class DetalleCompra_ { 

    public static volatile SingularAttribute<DetalleCompra, BigDecimal> subtotal;
    public static volatile SingularAttribute<DetalleCompra, BigDecimal> precioCompra;
    public static volatile SingularAttribute<DetalleCompra, Compra> idCompra;
    public static volatile SingularAttribute<DetalleCompra, Integer> idDetalleCompra;
    public static volatile SingularAttribute<DetalleCompra, Integer> cantidad;
    public static volatile SingularAttribute<DetalleCompra, Producto> idProducto;

}