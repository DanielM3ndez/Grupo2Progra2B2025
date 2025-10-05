package gt.edu.umg.tiendadb;

import gt.edu.umg.tiendadb.DetalleFactura;
import gt.edu.umg.tiendadb.Venta;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-10-04T08:39:32", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Factura.class)
public class Factura_ { 

    public static volatile SingularAttribute<Factura, String> estado;
    public static volatile SingularAttribute<Factura, Date> fechaRegistro;
    public static volatile SingularAttribute<Factura, BigDecimal> montoPago;
    public static volatile SingularAttribute<Factura, Venta> idVenta;
    public static volatile SingularAttribute<Factura, String> tipoDocumento;
    public static volatile CollectionAttribute<Factura, DetalleFactura> detalleFacturaCollection;
    public static volatile SingularAttribute<Factura, String> nombreCliente;
    public static volatile SingularAttribute<Factura, Integer> idFactura;
    public static volatile SingularAttribute<Factura, String> observaciones;
    public static volatile SingularAttribute<Factura, BigDecimal> montoCambio;
    public static volatile SingularAttribute<Factura, String> numeroDocumento;
    public static volatile SingularAttribute<Factura, String> documentoCliente;
    public static volatile SingularAttribute<Factura, BigDecimal> montoTotal;

}