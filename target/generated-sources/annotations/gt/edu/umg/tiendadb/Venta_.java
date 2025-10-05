package gt.edu.umg.tiendadb;

import gt.edu.umg.tiendadb.Cliente;
import gt.edu.umg.tiendadb.DetalleVenta;
import gt.edu.umg.tiendadb.Factura;
import gt.edu.umg.tiendadb.Usuario;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-10-04T08:39:32", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Venta.class)
public class Venta_ { 

    public static volatile SingularAttribute<Venta, String> estado;
    public static volatile SingularAttribute<Venta, Factura> factura;
    public static volatile SingularAttribute<Venta, Cliente> idCliente;
    public static volatile SingularAttribute<Venta, Date> fechaRegistro;
    public static volatile SingularAttribute<Venta, Usuario> idUsuario;
    public static volatile SingularAttribute<Venta, BigDecimal> montoTotal;
    public static volatile SingularAttribute<Venta, Integer> idVenta;
    public static volatile CollectionAttribute<Venta, DetalleVenta> detalleVentaCollection;

}