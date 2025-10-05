package gt.edu.umg.tiendadb;

import gt.edu.umg.tiendadb.Producto;
import gt.edu.umg.tiendadb.Venta;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-10-04T08:39:32", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(DetalleVenta.class)
public class DetalleVenta_ { 

    public static volatile SingularAttribute<DetalleVenta, BigDecimal> subtotal;
    public static volatile SingularAttribute<DetalleVenta, Integer> idDetalleVenta;
    public static volatile SingularAttribute<DetalleVenta, Integer> cantidad;
    public static volatile SingularAttribute<DetalleVenta, Producto> idProducto;
    public static volatile SingularAttribute<DetalleVenta, BigDecimal> precioVenta;
    public static volatile SingularAttribute<DetalleVenta, Venta> idVenta;

}