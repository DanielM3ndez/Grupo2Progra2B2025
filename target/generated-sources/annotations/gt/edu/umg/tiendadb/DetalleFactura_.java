package gt.edu.umg.tiendadb;

import gt.edu.umg.tiendadb.Factura;
import gt.edu.umg.tiendadb.Producto;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-10-04T08:39:32", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(DetalleFactura.class)
public class DetalleFactura_ { 

    public static volatile SingularAttribute<DetalleFactura, BigDecimal> precioUnitario;
    public static volatile SingularAttribute<DetalleFactura, BigDecimal> subtotal;
    public static volatile SingularAttribute<DetalleFactura, Factura> idFactura;
    public static volatile SingularAttribute<DetalleFactura, Integer> cantidad;
    public static volatile SingularAttribute<DetalleFactura, Producto> idProducto;
    public static volatile SingularAttribute<DetalleFactura, Integer> idDetalleFactura;

}