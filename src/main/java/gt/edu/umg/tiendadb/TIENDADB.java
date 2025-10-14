package gt.edu.umg.tiendadb;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class TIENDADB {

    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("gt.edu.umg_TIENDADB_jar_1.0-SNAPSHOTPU");

            // Instanciar controladores
            ProductoJpaController productoCtrl = new ProductoJpaController(emf);
            CompraJpaController compraCtrl = new CompraJpaController(emf);
            ProveedorJpaController proveedorCtrl = new ProveedorJpaController(emf);
            UsuarioJpaController usuarioCtrl = new UsuarioJpaController(emf);
            VentaJpaController ventaCtrl = new VentaJpaController(emf);     
            ClienteJpaController clienteCtrl = new ClienteJpaController(emf); 
            FacturaJpaController facturaCtrl = new FacturaJpaController(emf);
            DetalleFacturaJpaController detalleFacturaCtrl = new DetalleFacturaJpaController(emf);

            Scanner sc = new Scanner(System.in);
            int opcion;

            do {
                System.out.println("\n========= MENÚ PRINCIPAL =========");
                System.out.println("1. Ventas");
                System.out.println("2. Inventario");
                System.out.println("3. Compras");
                System.out.println("4. Facturación");
                System.out.println("5. Gestionar Proveedores");
                System.out.println("6. Gestionar Clientes");
                System.out.println("7. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = sc.nextInt();

                switch(opcion) {
                    case 1:
                        menuVentas(sc, productoCtrl, ventaCtrl, clienteCtrl, usuarioCtrl); // ← Actualizado
                        break;
                    case 2:
                        menuInventario(sc, productoCtrl);
                        break;
                    case 3:
                        menuCompras(sc, productoCtrl, compraCtrl, proveedorCtrl, usuarioCtrl);
                        break;
                    case 4:
                        menuFacturacion(sc, facturaCtrl, ventaCtrl, clienteCtrl, usuarioCtrl, detalleFacturaCtrl);
                        break;
                    case 5:
                        menuProveedores(sc, proveedorCtrl);
                        break;
                    case 6:
                        menuClientes(sc, clienteCtrl);
                        break;
                    case 7:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                        break;
                }
            } while(opcion != 7);

        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }

    // MENÚ PROVEEDORES
    public static void menuProveedores(Scanner sc, ProveedorJpaController proveedorCtrl) {
        int opcion;
        do {
            System.out.println("\n========= GESTIONAR PROVEEDORES =========");
            System.out.println("1. Listar proveedores");
            System.out.println("2. Agregar proveedor");
            System.out.println("3. Actualizar proveedor");
            System.out.println("4. Eliminar proveedor");
            System.out.println("5. Regresar");
            System.out.print("Opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    listarProveedores(proveedorCtrl);
                    break;
                case 2:
                    agregarProveedor(sc, proveedorCtrl);
                    break;
                case 3:
                    actualizarProveedor(sc, proveedorCtrl);
                    break;
                case 4:
                    eliminarProveedor(sc, proveedorCtrl);
                    break;
                case 5:
                    System.out.println("Regresando...");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (opcion != 5);
    }

    public static void listarProveedores(ProveedorJpaController proveedorCtrl) {
        System.out.println("\n📦 PROVEEDORES:");
        List<Proveedor> proveedores = proveedorCtrl.findProveedorEntities();
        if (proveedores.isEmpty()) {
            System.out.println("📭 No hay proveedores registrados.");
        } else {
            for (Proveedor p : proveedores) {
                System.out.printf("- ID: %d | Nombre: %s | Correo: %s | Teléfono: %s%n",
                    p.getIdProveedor(),
                    p.getNombre(),
                    p.getCorreo(),
                    p.getTelefono()
                );
            }
        }
    }

    public static void agregarProveedor(Scanner sc, ProveedorJpaController proveedorCtrl) {
        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Documento: ");
            String documento = sc.nextLine();
            System.out.print("Correo: ");
            String correo = sc.nextLine();
            System.out.print("Teléfono: ");
            String telefono = sc.nextLine();
            System.out.print("Dirección: ");
            String direccion = sc.nextLine();

            Proveedor p = new Proveedor();
            p.setNombre(nombre);
            p.setDocumento(documento);
            p.setCorreo(correo);
            p.setTelefono(telefono);
            p.setDireccion(direccion);

            proveedorCtrl.create(p);
            System.out.println("✅ Proveedor creado con ID: " + p.getIdProveedor());
        } catch (Exception e) {
            System.out.println("❌ Error al crear: " + e.getMessage());
        }
    }

    public static void actualizarProveedor(Scanner sc, ProveedorJpaController proveedorCtrl) {
        try {
            System.out.print("ID a actualizar: ");
            int id = sc.nextInt();
            sc.nextLine();

            Proveedor p = proveedorCtrl.findProveedor(id);
            if (p == null) {
                System.out.println("❌ Proveedor no encontrado.");
                return;
            }

            System.out.print("Nuevo nombre (" + p.getNombre() + "): ");
            String nombre = sc.nextLine();
            if (!nombre.isEmpty()) p.setNombre(nombre);

            System.out.print("Nuevo documento (" + p.getDocumento() + "): ");
            String documento = sc.nextLine();
            if (!documento.isEmpty()) p.setDocumento(documento);

            System.out.print("Nuevo correo (" + p.getCorreo() + "): ");
            String correo = sc.nextLine();
            if (!correo.isEmpty()) p.setCorreo(correo);

            System.out.print("Nuevo teléfono (" + p.getTelefono() + "): ");
            String telefono = sc.nextLine();
            if (!telefono.isEmpty()) p.setTelefono(telefono);

            System.out.print("Nueva dirección (" + p.getDireccion() + "): ");
            String direccion = sc.nextLine();
            if (!direccion.isEmpty()) p.setDireccion(direccion);

            proveedorCtrl.edit(p);
            System.out.println("✅ Proveedor actualizado.");
        } catch (Exception e) {
            System.out.println("❌ Error al actualizar: " + e.getMessage());
        }
    }

    public static void eliminarProveedor(Scanner sc, ProveedorJpaController proveedorCtrl) {
        try {
            System.out.print("ID a eliminar: ");
            int id = sc.nextInt();

            Proveedor p = proveedorCtrl.findProveedor(id);
            if (p == null) {
                System.out.println("❌ Proveedor no encontrado.");
                return;
            }

            System.out.println("¿Eliminar '" + p.getNombre() + "'? (s/n)");
            String confirm = sc.next();
            if (confirm.toLowerCase().startsWith("s")) {
                proveedorCtrl.destroy(id);
                System.out.println("✅ Proveedor eliminado.");
            } else {
                System.out.println("❌ Cancelado.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error al eliminar: " + e.getMessage());
        }
    }
    
    // MENÚ CLIENTES
    public static void menuClientes(Scanner sc, ClienteJpaController clienteCtrl) {
        int opcion;
        do {
            System.out.println("\n========= GESTIONAR CLIENTES =========");
            System.out.println("1. Listar clientes");
            System.out.println("2. Agregar cliente");
            System.out.println("3. Actualizar cliente");
            System.out.println("4. Eliminar cliente");
            System.out.println("5. Regresar");
            System.out.print("Opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    listarClientes(clienteCtrl);
                    break;
                case 2:
                    agregarCliente(sc, clienteCtrl);
                    break;
                case 3:
                    actualizarCliente(sc, clienteCtrl);
                    break;
                case 4:
                    eliminarCliente(sc, clienteCtrl);
                    break;
                case 5:
                    System.out.println("Regresando...");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (opcion != 5);
    }
    
    // LISTAR CLIENTES
    public static void listarClientes(ClienteJpaController clienteCtrl) {
        System.out.println("\n📦 CLIENTES:");
        List<Cliente> clientes = clienteCtrl.findClienteEntities();
        if (clientes.isEmpty()) {
            System.out.println("📭 No hay clientes registrados.");
        } else {
            for (Cliente c : clientes) {
                System.out.printf("- ID: %d | Nombre: %s | Correo: %s | Teléfono: %s%n",
                    c.getIdCliente(),
                    c.getNombre(),
                    c.getCorreo(),
                    c.getTelefono()
                );
            }
        }
    }

    // AGREGAR CLIENTE
    public static void agregarCliente(Scanner sc, ClienteJpaController clienteCtrl) {
        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Documento: ");
            String documento = sc.nextLine();
            System.out.print("Correo: ");
            String correo = sc.nextLine();
            System.out.print("Teléfono: ");
            String telefono = sc.nextLine();

            Cliente c = new Cliente();
            c.setNombre(nombre);
            c.setDocumento(documento);
            c.setCorreo(correo);
            c.setTelefono(telefono);

            clienteCtrl.create(c);
            System.out.println("✅ Cliente creado con ID: " + c.getIdCliente());
        } catch (Exception e) {
            System.out.println("❌ Error al crear: " + e.getMessage());
        }
    }

    // ACTUALIZAR CLIENTE
    public static void actualizarCliente(Scanner sc, ClienteJpaController clienteCtrl) {
        try {
            System.out.print("ID a actualizar: ");
            int id = sc.nextInt();
            sc.nextLine();

            Cliente c = clienteCtrl.findCliente(id);
            if (c == null) {
                System.out.println("❌ Cliente no encontrado.");
                return;
            }

            System.out.print("Nuevo nombre (" + c.getNombre() + "): ");
            String nombre = sc.nextLine();
            if (!nombre.isEmpty()) c.setNombre(nombre);

            System.out.print("Nuevo documento (" + c.getDocumento() + "): ");
            String documento = sc.nextLine();
            if (!documento.isEmpty()) c.setDocumento(documento);

            System.out.print("Nuevo correo (" + c.getCorreo() + "): ");
            String correo = sc.nextLine();
            if (!correo.isEmpty()) c.setCorreo(correo);

            System.out.print("Nuevo teléfono (" + c.getTelefono() + "): ");
            String telefono = sc.nextLine();
            if (!telefono.isEmpty()) c.setTelefono(telefono);


            clienteCtrl.edit(c);
            System.out.println("✅ Cliente actualizado.");
        } catch (Exception e) {
            System.out.println("❌ Error al actualizar: " + e.getMessage());
        }
    }

    // ELIMINAR CLIENTE
    public static void eliminarCliente(Scanner sc, ClienteJpaController clienteCtrl) {
        try {
            System.out.print("ID a eliminar: ");
            int id = sc.nextInt();

            Cliente c = clienteCtrl.findCliente(id);
            if (c == null) {
                System.out.println("❌ Cliente no encontrado.");
                return;
            }

            System.out.println("¿Eliminar '" + c.getNombre() + "'? (s/n)");
            String confirm = sc.next();
            if (confirm.toLowerCase().startsWith("s")) {
                clienteCtrl.destroy(id);
                System.out.println("✅ Cliente eliminado.");
            } else {
                System.out.println("❌ Cancelado.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error al eliminar: " + e.getMessage());
        }
    }
    
    
    // MENÚ COMPRAS
    public static void menuCompras(Scanner sc, 
                                  ProductoJpaController productoCtrl,
                                  CompraJpaController compraCtrl,
                                  ProveedorJpaController proveedorCtrl,
                                  UsuarioJpaController usuarioCtrl) {
        
        int opcion;
        do {
            System.out.println("\n========= MENÚ COMPRAS =========");
            System.out.println("1. Nueva Compra");
            System.out.println("2. Listar Compras");
            System.out.println("3. Regresar");
            System.out.print("Opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    crearCompra(sc, productoCtrl, compraCtrl, proveedorCtrl, usuarioCtrl);
                    break;
                case 2:
                    listarCompras(compraCtrl);
                    break;
                case 3:
                    System.out.println("Regresando...");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (opcion != 3);
    }

    // CREAR COMPRA
    public static void crearCompra(Scanner sc, 
                                  ProductoJpaController productoCtrl,
                                  CompraJpaController compraCtrl,
                                  ProveedorJpaController proveedorCtrl,
                                  UsuarioJpaController usuarioCtrl) {
        
        try {
            // Seleccionar proveedor
            System.out.println("\n--- SELECCIONAR PROVEEDOR ---");
            List<Proveedor> proveedores = proveedorCtrl.findProveedorEntities();
            if (proveedores.isEmpty()) {
                System.out.println("❌ No hay proveedores registrados.");
                return;
            }
            for (int i = 0; i < proveedores.size(); i++) {
                Proveedor p = proveedores.get(i);
                System.out.printf("%d. %s (ID: %d)%n", i + 1, p.getNombre(), p.getIdProveedor());
            }
            System.out.print("Seleccione proveedor: ");
            int proveedorIndex = sc.nextInt() - 1;
            Proveedor proveedor = proveedores.get(proveedorIndex);

            // Seleccionar usuario
            System.out.println("\n--- SELECCIONAR USUARIO ---");
            List<Usuario> usuarios = usuarioCtrl.findUsuarioEntities();
            if (usuarios.isEmpty()) {
                System.out.println("❌ No hay usuarios registrados.");
                return;
            }
            for (int i = 0; i < usuarios.size(); i++) {
                Usuario u = usuarios.get(i);
                System.out.printf("%d. %s (ID: %d)%n", i + 1, u.getNombreCompleto(), u.getIdUsuario());
            }
            System.out.print("Seleccione usuario: ");
            int usuarioIndex = sc.nextInt() - 1;
            Usuario usuario = usuarios.get(usuarioIndex);

            // Crear nueva compra
            Compra compra = new Compra();
            compra.setIdProveedor(proveedor);
            compra.setIdUsuario(usuario);
            compra.setMontoTotal(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            compra.setFechaRegistro(new java.util.Date());
            compra.setEstado("PENDIENTE");

            compraCtrl.create(compra);
            System.out.println("✅ Compra creada con ID: " + compra.getIdCompra());

            // Agregar productos
            boolean continuar = true;
            double totalCompra = 0.0;
            while (continuar) {
                System.out.println("\n--- PRODUCTOS DISPONIBLES ---");
                List<Producto> productos = productoCtrl.findProductoEntities();
                for (int i = 0; i < productos.size(); i++) {
                    Producto p = productos.get(i);
                    System.out.printf("%d. %s - Q%s (Stock: %d)%n", 
                        i + 1, p.getNombre(), p.getPrecioCompra(), p.getStock());
                }
                System.out.print("Seleccione producto (0 para terminar): ");
                int productoIndex = sc.nextInt();
                if (productoIndex == 0) {
                    continuar = false;
                    continue;
                }
                if (productoIndex < 1 || productoIndex > productos.size()) {
                    System.out.println("⚠️ Producto inválido.");
                    continue;
                }
                Producto producto = productos.get(productoIndex - 1);

                System.out.print("Cantidad: ");
                int cantidad = sc.nextInt();
                if (cantidad <= 0) {
                    System.out.println("⚠️ Cantidad inválida.");
                    continue;
                }

                //  USAR SQL NATIVO PARA EVITAR EL ERROR DE subtotal
                insertarDetalleCompra(compra.getIdCompra(), producto.getIdProducto(), cantidad, producto.getPrecioCompra());
                System.out.println("✅ Detalle agregado: " + cantidad + " x " + producto.getNombre());

                // Actualizar stock del producto
                producto.setStock(producto.getStock() + cantidad);
                productoCtrl.edit(producto);

                // Actualizar monto total de la compra
                totalCompra += producto.getPrecioCompra().doubleValue() * cantidad;
                compra.setMontoTotal(BigDecimal.valueOf(totalCompra).setScale(2, RoundingMode.HALF_UP));
                compraCtrl.edit(compra);
            }

            // Confirmar compra
            compra.setEstado("CONFIRMADA");
            compraCtrl.edit(compra);
            System.out.println("🎉 Compra confirmada con ID: " + compra.getIdCompra());
            System.out.println("💰 Total: Q" + compra.getMontoTotal());

        } catch (Exception e) {
            System.out.println("❌ Error al crear compra: " + e.getMessage());
        }
    }

    //  MÉTODO AUXILIAR: INSERTAR DETALLE CON SQL NATIVO (SIN subtotal)
    public static void insertarDetalleCompra(int idCompra, int idProducto, int cantidad, BigDecimal precioCompra) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gt.edu.umg_TIENDADB_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            // SQL nativo: NO incluimos subtotal (la BD lo calcula)
            String sql = "INSERT INTO DetalleCompra (idCompra, idProducto, cantidad, precioCompra) VALUES (?, ?, ?, ?)";
            em.createNativeQuery(sql)
              .setParameter(1, idCompra)
              .setParameter(2, idProducto)
              .setParameter(3, cantidad)
              .setParameter(4, precioCompra)
              .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    // LISTAR COMPRAS
    public static void listarCompras(CompraJpaController compraCtrl) {
        System.out.println("\n📦 LISTA DE COMPRAS:");
        List<Compra> compras = compraCtrl.findCompraEntities();
        if (compras.isEmpty()) {
            System.out.println("📭 No hay compras registradas.");
        } else {
            for (Compra c : compras) {
                System.out.printf("- ID: %d | Proveedor: %d | Usuario: %d | Total: Q%s | Fecha: %s | Estado: %s%n",
                    c.getIdCompra(),
                    c.getIdProveedor().getIdProveedor(),
                    c.getIdUsuario().getIdUsuario(),
                    c.getMontoTotal(),
                    c.getFechaRegistro(),
                    c.getEstado()
                );
            }
        }
    }

    // INVENTARIO
    public static void menuInventario(Scanner sc, ProductoJpaController productoCtrl) {
        int opcion;
        do {
            System.out.println("\n========= INVENTARIO =========");
            System.out.println("1. Listar productos");
            System.out.println("2. Agregar producto");
            System.out.println("3. Actualizar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("5. Regresar");
            System.out.print("Opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    listarProductos(productoCtrl);
                    break;
                case 2:
                    agregarProducto(sc, productoCtrl);
                    break;
                case 3:
                    actualizarProducto(sc, productoCtrl);
                    break;
                case 4:
                    eliminarProducto(sc, productoCtrl);
                    break;
                case 5:
                    System.out.println("Regresando...");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (opcion != 5);
    }

    public static void listarProductos(ProductoJpaController productoCtrl) {
        System.out.println("\n📦 PRODUCTOS:");
        List<Producto> productos = productoCtrl.findProductoEntities();
        if (productos.isEmpty()) {
            System.out.println("📭 No hay productos.");
        } else {
            for (Producto p : productos) {
                System.out.printf("- ID: %d | Nombre: %s | Venta: Q%s | Stock: %d | Categoría: %d%n",
                    p.getIdProducto(),
                    p.getNombre(),
                    p.getPrecioVenta(),
                    p.getStock(),
                    p.getIdCategoria().getIdCategoria()
                );
            }
        }
    }

    public static void agregarProducto(Scanner sc, ProductoJpaController productoCtrl) {
        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Precio compra: ");
            double pc = sc.nextDouble();
            System.out.print("Precio venta: ");
            double pv = sc.nextDouble();
            System.out.print("Stock: ");
            int stock = sc.nextInt();
            System.out.print("ID Categoría: ");
            int idCat = sc.nextInt();

            Producto p = new Producto();
            p.setNombre(nombre);
            p.setPrecioCompra(BigDecimal.valueOf(pc).setScale(2, RoundingMode.HALF_UP));
            p.setPrecioVenta(BigDecimal.valueOf(pv).setScale(2, RoundingMode.HALF_UP));
            p.setStock(stock);
            
            Categoria cat = new Categoria();
            cat.setIdCategoria(idCat);
            p.setIdCategoria(cat);

            productoCtrl.create(p);
            System.out.println("✅ Producto creado con ID: " + p.getIdProducto());
        } catch (Exception e) {
            System.out.println("❌ Error al crear: " + e.getMessage());
        }
    }

    public static void actualizarProducto(Scanner sc, ProductoJpaController productoCtrl) {
        try {
            System.out.print("ID a actualizar: ");
            int id = sc.nextInt();
            sc.nextLine();

            Producto p = productoCtrl.findProducto(id);
            if (p == null) {
                System.out.println("❌ Producto no encontrado.");
                return;
            }

            System.out.print("Nuevo nombre (" + p.getNombre() + "): ");
            String nombre = sc.nextLine();
            if (!nombre.isEmpty()) p.setNombre(nombre);

            System.out.print("Nuevo precio venta (" + p.getPrecioVenta() + "): ");
            String pvStr = sc.nextLine();
            if (!pvStr.isEmpty()) {
                double pv = Double.parseDouble(pvStr);
                p.setPrecioVenta(BigDecimal.valueOf(pv).setScale(2, RoundingMode.HALF_UP));
            }

            System.out.print("Nuevo stock (" + p.getStock() + "): ");
            String stockStr = sc.nextLine();
            if (!stockStr.isEmpty()) {
                p.setStock(Integer.parseInt(stockStr));
            }

            productoCtrl.edit(p);
            System.out.println("✅ Producto actualizado.");
        } catch (Exception e) {
            System.out.println("❌ Error al actualizar: " + e.getMessage());
        }
    }

    public static void eliminarProducto(Scanner sc, ProductoJpaController productoCtrl) {
        try {
            System.out.print("ID a eliminar: ");
            int id = sc.nextInt();

            Producto p = productoCtrl.findProducto(id);
            if (p == null) {
                System.out.println("❌ Producto no encontrado.");
                return;
            }

            System.out.println("¿Eliminar '" + p.getNombre() + "'? (s/n)");
            String confirm = sc.next();
            if (confirm.toLowerCase().startsWith("s")) {
                productoCtrl.destroy(id);
                System.out.println("✅ Producto eliminado.");
            } else {
                System.out.println("❌ Cancelado.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error al eliminar: " + e.getMessage());
        }
    }

    // MENÚ VENTAS
    public static void menuVentas(Scanner sc, 
                                 ProductoJpaController productoCtrl,
                                 VentaJpaController ventaCtrl,
                                 ClienteJpaController clienteCtrl,
                                 UsuarioJpaController usuarioCtrl) {

        int opcion;
        do {
            System.out.println("\n========= MENÚ VENTAS =========");
            System.out.println("1. Nueva Venta");
            System.out.println("2. Listar Ventas");
            System.out.println("3. Regresar");
            System.out.print("Opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    crearVenta(sc, productoCtrl, ventaCtrl, clienteCtrl, usuarioCtrl);
                    break;
                case 2:
                    listarVentas(ventaCtrl);
                    break;
                case 3:
                    System.out.println("Regresando...");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (opcion != 3);
    }
    // CREAR VENTA
    public static void crearVenta(Scanner sc, 
                                 ProductoJpaController productoCtrl,
                                 VentaJpaController ventaCtrl,
                                 ClienteJpaController clienteCtrl,
                                 UsuarioJpaController usuarioCtrl) {

        try {
            // Seleccionar cliente
            System.out.println("\n--- SELECCIONAR CLIENTE ---");
            List<Cliente> clientes = clienteCtrl.findClienteEntities();
            if (clientes.isEmpty()) {
                System.out.println("❌ No hay clientes registrados.");
                return;
            }
            for (int i = 0; i < clientes.size(); i++) {
                Cliente c = clientes.get(i);
                System.out.printf("%d. %s (ID: %d)%n", i + 1, c.getNombre(), c.getIdCliente());
            }
            System.out.print("Seleccione cliente: ");
            int clienteIndex = sc.nextInt() - 1;
            Cliente cliente = clientes.get(clienteIndex);

            // Seleccionar usuario
            System.out.println("\n--- SELECCIONAR USUARIO ---");
            List<Usuario> usuarios = usuarioCtrl.findUsuarioEntities();
            if (usuarios.isEmpty()) {
                System.out.println("❌ No hay usuarios registrados.");
                return;
            }
            for (int i = 0; i < usuarios.size(); i++) {
                Usuario u = usuarios.get(i);
                System.out.printf("%d. %s (ID: %d)%n", i + 1, u.getNombreCompleto(), u.getIdUsuario());
            }
            System.out.print("Seleccione usuario: ");
            int usuarioIndex = sc.nextInt() - 1;
            Usuario usuario = usuarios.get(usuarioIndex);

            // Crear nueva venta
            Venta venta = new Venta();
            venta.setIdCliente(cliente);
            venta.setIdUsuario(usuario);
            venta.setMontoTotal(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            venta.setFechaRegistro(new java.util.Date());
            venta.setEstado("PENDIENTE");

            ventaCtrl.create(venta);
            System.out.println("✅ Venta creada con ID: " + venta.getIdVenta());

            // Agregar productos
            boolean continuar = true;
            double totalVenta = 0.0;
            while (continuar) {
                System.out.println("\n--- PRODUCTOS DISPONIBLES ---");
                List<Producto> productos = productoCtrl.findProductoEntities();
                for (int i = 0; i < productos.size(); i++) {
                    Producto p = productos.get(i);
                    System.out.printf("%d. %s - Q%s (Stock: %d)%n", 
                        i + 1, p.getNombre(), p.getPrecioVenta(), p.getStock());
                }
                System.out.print("Seleccione producto (0 para terminar): ");
                int productoIndex = sc.nextInt();
                if (productoIndex == 0) {
                    continuar = false;
                    continue;
                }
                if (productoIndex < 1 || productoIndex > productos.size()) {
                    System.out.println("⚠️ Producto inválido.");
                    continue;
                }
                Producto producto = productos.get(productoIndex - 1);

                System.out.print("Cantidad: ");
                int cantidad = sc.nextInt();
                if (cantidad <= 0 || cantidad > producto.getStock()) {
                    System.out.println("⚠️ Cantidad inválida o sin stock suficiente.");
                    continue;
                }

                //  USAR SQL NATIVO PARA EVITAR EL ERROR DE subtotal
                insertarDetalleVenta(venta.getIdVenta(), producto.getIdProducto(), cantidad, producto.getPrecioVenta());
                System.out.println("✅ Detalle agregado: " + cantidad + " x " + producto.getNombre());

                // Actualizar stock del producto (¡restamos porque es una venta!)
                producto.setStock(producto.getStock() - cantidad);
                productoCtrl.edit(producto);

                // Actualizar monto total de la venta
                totalVenta += producto.getPrecioVenta().doubleValue() * cantidad;
                venta.setMontoTotal(BigDecimal.valueOf(totalVenta).setScale(2, RoundingMode.HALF_UP));
                ventaCtrl.edit(venta);
            }

            // Confirmar venta
            venta.setEstado("CONFIRMADA");
            ventaCtrl.edit(venta);
            System.out.println("🎉 Venta confirmada con ID: " + venta.getIdVenta());
            System.out.println("💰 Total: Q" + venta.getMontoTotal());

        } catch (Exception e) {
            System.out.println("❌ Error al crear venta: " + e.getMessage());
        }
    }
    
    // LISTAR VENTAS
    public static void listarVentas(VentaJpaController ventaCtrl) {
        System.out.println("\n📦 LISTA DE VENTAS:");
        List<Venta> ventas = ventaCtrl.findVentaEntities();
        if (ventas.isEmpty()) {
            System.out.println("📭 No hay ventas registradas.");
        } else {
            for (Venta v : ventas) {
                System.out.printf("- ID: %d | Cliente: %d | Usuario: %d | Total: Q%s | Fecha: %s | Estado: %s%n",
                    v.getIdVenta(),
                    v.getIdCliente().getIdCliente(),
                    v.getIdUsuario().getIdUsuario(),
                    v.getMontoTotal(),
                    v.getFechaRegistro(),
                    v.getEstado()
                );
            }
        }
    }
    
    // MÉTODO AUXILIAR: INSERTAR DETALLE DE VENTA CON SQL NATIVO (SIN subtotal)
    public static void insertarDetalleVenta(int idVenta, int idProducto, int cantidad, BigDecimal precioVenta) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gt.edu.umg_TIENDADB_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            // SQL nativo: NO incluimos subtotal (la BD lo calcula)
            String sql = "INSERT INTO DetalleVenta (idVenta, idProducto, cantidad, precioVenta) VALUES (?, ?, ?, ?)";
            em.createNativeQuery(sql)
              .setParameter(1, idVenta)
              .setParameter(2, idProducto)
              .setParameter(3, cantidad)
              .setParameter(4, precioVenta)
              .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    // MENÚ FACTURACIÓN
    public static void menuFacturacion(Scanner sc, 
                                      FacturaJpaController facturaCtrl,
                                      VentaJpaController ventaCtrl,
                                      ClienteJpaController clienteCtrl,
                                      UsuarioJpaController usuarioCtrl,
                                      DetalleFacturaJpaController detalleFacturaCtrl) {
        int opcion;
        do {
            System.out.println("\n========= FACTURACIÓN =========");
            System.out.println("1. Generar factura desde venta");
            System.out.println("2. Listar facturas activas");
            System.out.println("3. Listar facturas anuladas");
            System.out.println("4. Ver detalles de factura");
            System.out.println("5. Anular factura");
            System.out.println("6. Regresar");
            System.out.print("Opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    generarFactura(sc, facturaCtrl, ventaCtrl, clienteCtrl, detalleFacturaCtrl);
                    break;
                case 2:
                    listarFacturas(facturaCtrl, ventaCtrl, clienteCtrl, usuarioCtrl, "ACTIVA");
                    break;
                case 3:
                    listarFacturas(facturaCtrl, ventaCtrl, clienteCtrl, usuarioCtrl, "ANULADA");
                    break;
                case 4:
                    verDetallesFactura(sc, detalleFacturaCtrl, facturaCtrl);
                    break;
                case 5:
                    anularFactura(sc, facturaCtrl);
                    break;
                case 6:
                    System.out.println("Regresando...");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (opcion != 6);
    }
    
    // GENERAR FACTURA
    public static void generarFactura(Scanner sc, 
                                     FacturaJpaController facturaCtrl,
                                     VentaJpaController ventaCtrl,
                                     ClienteJpaController clienteCtrl,
                                     DetalleFacturaJpaController detalleFacturaCtrl) {
        try {
            // Listar ventas confirmadas sin factura
            System.out.println("\n--- VENTAS DISPONIBLES PARA FACTURAR ---");
            List<Venta> ventas = ventaCtrl.findVentaEntities();
            List<Venta> ventasDisponibles = new ArrayList<>();
            int index = 1;

            for (Venta v : ventas) {
                if ("CONFIRMADA".equals(v.getEstado())) {
                    boolean tieneFactura = false;
                    EntityManagerFactory emf = Persistence.createEntityManagerFactory("gt.edu.umg_TIENDADB_jar_1.0-SNAPSHOTPU");
                    EntityManager em = emf.createEntityManager();
                    try {
                        Query query = em.createQuery("SELECT f FROM Factura f WHERE f.idVenta.idVenta = :idVenta");
                        query.setParameter("idVenta", v.getIdVenta());
                        if (!query.getResultList().isEmpty()) {
                            tieneFactura = true;
                        }
                    } finally {
                        em.close();
                        emf.close();
                    }

                    if (!tieneFactura) {
                        System.out.printf("%d. Venta ID: %d | Total: Q%s | Fecha: %s%n",
                            index, v.getIdVenta(), v.getMontoTotal(), v.getFechaRegistro());
                        ventasDisponibles.add(v);
                        index++;
                    }
                }
            }

            if (ventasDisponibles.isEmpty()) {
                System.out.println("❌ No hay ventas confirmadas sin factura.");
                return;
            }

            System.out.print("Seleccione venta para facturar: ");
            int ventaIndex = sc.nextInt();
            sc.nextLine(); // ✅ LIMPIAR BUFFER AQUÍ

            if (ventaIndex < 1 || ventaIndex >= index) {
                System.out.println("⚠️ Venta inválida.");
                return;
            }
            Venta venta = ventasDisponibles.get(ventaIndex - 1);
            Cliente cliente = clienteCtrl.findCliente(venta.getIdCliente().getIdCliente());

            // Ingresar serie y número
            System.out.print("Serie de factura: ");
            String serie = sc.nextLine(); // ✅ Ahora funcionará
            System.out.print("Número de factura: ");
            String numero = sc.nextLine();

            // Crear factura con TODOS los campos obligatorios
            Factura factura = new Factura();
            factura.setSerie(serie);
            factura.setNoFactura(numero);
            factura.setNombreCliente(cliente.getNombre());
            factura.setDocumentoCliente(cliente.getDocumento());
            factura.setMontoTotal(venta.getMontoTotal());
            factura.setMontoPago(venta.getMontoTotal()); // o pide el monto pago si es diferente
            factura.setMontoCambio(BigDecimal.ZERO);     // o calcula el cambio si aplica
            factura.setFechaRegistro(new java.util.Date());
            factura.setEstado("ACTIVA");
            factura.setObservaciones(""); // o pide observaciones
            factura.setIdVenta(venta);

            facturaCtrl.create(factura);
            System.out.println("✅ Factura generada con ID: " + factura.getIdFactura());
               
            venta.setFactura(factura); // ← Establecer la relación en la Venta
            ventaCtrl.edit(venta);     // ← Guardar la Venta actualizada
            // Copiar detalles
            copiarDetallesVentaAFactura(venta.getIdVenta(), factura.getIdFactura());

            // Actualizar venta
            venta.setEstado("FACTURADA");
            ventaCtrl.edit(venta);
            System.out.println("✅ Venta actualizada a estado FACTURADA.");

        } catch (Exception e) {
            System.out.println("❌ Error al generar factura: " + e.getMessage());
        }
    }
    
    // COPIAR DETALLES DE VENTA A FACTURA (USANDO SQL NATIVO)
    public static void copiarDetallesVentaAFactura(int idVenta, int idFactura) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gt.edu.umg_TIENDADB_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            // Primero obtenemos los detalles de la venta
            String sqlSelect = "SELECT idProducto, cantidad, precioVenta FROM DetalleVenta WHERE idVenta = ?";
            List<Object[]> detallesVenta = em.createNativeQuery(sqlSelect)
                                             .setParameter(1, idVenta)
                                             .getResultList();

            // Insertamos cada detalle en DetalleFactura (sin subtotal)
            for (Object[] detalle : detallesVenta) {
                Integer idProducto = ((Number) detalle[0]).intValue();
                Integer cantidad = ((Number) detalle[1]).intValue();
                BigDecimal precioVenta = (BigDecimal) detalle[2];

                String sqlInsert = "INSERT INTO DetalleFactura (idFactura, idProducto, cantidad, precioUnitario) VALUES (?, ?, ?, ?)";
                em.createNativeQuery(sqlInsert)
                  .setParameter(1, idFactura)
                  .setParameter(2, idProducto)
                  .setParameter(3, cantidad)
                  .setParameter(4, precioVenta)
                  .executeUpdate();
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    // LISTAR FACTURAS (POR ESTADO)
    public static void listarFacturas(FacturaJpaController facturaCtrl,
                                     VentaJpaController ventaCtrl,
                                     ClienteJpaController clienteCtrl,
                                     UsuarioJpaController usuarioCtrl,
                                     String estado) {
        System.out.println("\n📦 FACTURAS " + (estado.equals("ACTIVA") ? "ACTIVAS" : "ANULADAS") + ":");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gt.edu.umg_TIENDADB_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();

        try {
            // Buscar facturas por estado
            String jpql = "SELECT f FROM Factura f WHERE f.estado = :estado";
            TypedQuery<Factura> query = em.createQuery(jpql, Factura.class);
            query.setParameter("estado", estado);
            List<Factura> facturas = query.getResultList();

            if (facturas.isEmpty()) {
                System.out.println("📭 No hay facturas " + (estado.equals("ACTIVA") ? "activas" : "anuladas") + ".");
            } else {
                for (Factura f : facturas) {
                    // Obtener datos de la venta
                    Venta v = ventaCtrl.findVenta(f.getIdVenta().getIdVenta());
                    Cliente c = clienteCtrl.findCliente(v.getIdCliente().getIdCliente());
                    Usuario u = usuarioCtrl.findUsuario(v.getIdUsuario().getIdUsuario());

                    System.out.printf("- ID: %d |Factura:  %s-%s | Cliente: %s | Total: Q%s | Fecha: %s%n",
                    f.getIdFactura(),
                    f.getSerie(), f.getNoFactura(),
                    c.getNombre(), 
                    f.getMontoTotal(), 
                    f.getFechaRegistro());
                }
            }
        } finally {
            em.close();
            emf.close();
        }
    }

    // VER DETALLES DE FACTURA
    public static void verDetallesFactura(Scanner sc, 
                                         DetalleFacturaJpaController detalleFacturaCtrl,
                                         FacturaJpaController facturaCtrl) {
        try {
            System.out.print("ID de la factura: ");
            int idFactura = sc.nextInt();

            Factura f = facturaCtrl.findFactura(idFactura);
            if (f == null) {
                System.out.println("❌ Factura no encontrada.");
                return;
            }

            System.out.println("\n📄 DETALLES DE FACTURA " + f.getSerie() + "-" + f.getNoFactura());
            System.out.println("Total: Q" + f.getMontoTotal());
            System.out.println("Estado: " + f.getEstado());
            System.out.println("----------------------------");

            // Obtener detalles usando SQL nativo (para evitar problemas con subtotal)
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("gt.edu.umg_TIENDADB_jar_1.0-SNAPSHOTPU");
            EntityManager em = emf.createEntityManager();

            try {
                String sql = "SELECT df.idProducto, df.cantidad, df.precioUnitario, p.nombre " +
                            "FROM DetalleFactura df " +
                            "JOIN Producto p ON df.idProducto = p.idProducto " +
                            "WHERE df.idFactura = ?";
                List<Object[]> detalles = em.createNativeQuery(sql)
                                           .setParameter(1, idFactura)
                                           .getResultList();

                if (detalles.isEmpty()) {
                    System.out.println("📭 Sin detalles.");
                } else {
                    for (Object[] detalle : detalles) {
                        String nombreProducto = (String) detalle[3];
                        Integer cantidad = ((Number) detalle[1]).intValue();
                        BigDecimal precioVenta = (BigDecimal) detalle[2];
                        BigDecimal subtotal = precioVenta.multiply(BigDecimal.valueOf(cantidad));

                        System.out.printf("- %s | Cantidad: %d | Precio: Q%s | Subtotal: Q%s%n",
                            nombreProducto, cantidad, precioVenta, subtotal);
                    }
                }
            } finally {
                em.close();
                emf.close();
            }
        } catch (Exception e) {
            System.out.println("❌ Error al ver detalles: " + e.getMessage());
        }
    }
    
    // ANULAR FACTURA
    public static void anularFactura(Scanner sc, FacturaJpaController facturaCtrl) {
        try {
            System.out.print("ID de la factura a anular: ");
            int idFactura = sc.nextInt();

            Factura f = facturaCtrl.findFactura(idFactura);
            if (f == null) {
                System.out.println("❌ Factura no encontrada.");
                return;
            }

            if ("ANULADA".equals(f.getEstado())) {
                System.out.println("⚠️ La factura ya está anulada.");
                return;
            }

            System.out.println("¿Anular factura " + f.getSerie() + "-" + f.getNoFactura() + "? (s/n)");
            String confirm = sc.next();
            if (confirm.toLowerCase().startsWith("s")) {
                f.setEstado("ANULADA");
                facturaCtrl.edit(f);
                System.out.println("✅ Factura anulada exitosamente.");
            } else {
                System.out.println("❌ Anulación cancelada.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error al anular factura: " + e.getMessage());
        }
    }

}