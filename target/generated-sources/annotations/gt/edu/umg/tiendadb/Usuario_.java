package gt.edu.umg.tiendadb;

import gt.edu.umg.tiendadb.Compra;
import gt.edu.umg.tiendadb.Rol;
import gt.edu.umg.tiendadb.Venta;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-10-04T08:39:32", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> clave;
    public static volatile SingularAttribute<Usuario, Rol> idRol;
    public static volatile CollectionAttribute<Usuario, Venta> ventaCollection;
    public static volatile CollectionAttribute<Usuario, Compra> compraCollection;
    public static volatile SingularAttribute<Usuario, Integer> idUsuario;
    public static volatile SingularAttribute<Usuario, String> correo;
    public static volatile SingularAttribute<Usuario, String> nombreCompleto;

}