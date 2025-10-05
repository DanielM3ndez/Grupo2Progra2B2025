package gt.edu.umg.tiendadb;

import gt.edu.umg.tiendadb.DetalleCompra;
import gt.edu.umg.tiendadb.Proveedor;
import gt.edu.umg.tiendadb.Usuario;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-10-04T08:39:32", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Compra.class)
public class Compra_ { 

    public static volatile SingularAttribute<Compra, String> estado;
    public static volatile CollectionAttribute<Compra, DetalleCompra> detalleCompraCollection;
    public static volatile SingularAttribute<Compra, Date> fechaRegistro;
    public static volatile SingularAttribute<Compra, Integer> idCompra;
    public static volatile SingularAttribute<Compra, Proveedor> idProveedor;
    public static volatile SingularAttribute<Compra, Usuario> idUsuario;
    public static volatile SingularAttribute<Compra, BigDecimal> montoTotal;

}