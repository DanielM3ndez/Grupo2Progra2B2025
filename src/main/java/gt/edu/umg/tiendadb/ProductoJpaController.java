/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.tiendadb;

import gt.edu.umg.tiendadb.exceptions.IllegalOrphanException;
import gt.edu.umg.tiendadb.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Daniel
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getDetalleVentaCollection() == null) {
            producto.setDetalleVentaCollection(new ArrayList<DetalleVenta>());
        }
        if (producto.getDetalleCompraCollection() == null) {
            producto.setDetalleCompraCollection(new ArrayList<DetalleCompra>());
        }
        if (producto.getDetalleFacturaCollection() == null) {
            producto.setDetalleFacturaCollection(new ArrayList<DetalleFactura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria idCategoria = producto.getIdCategoria();
            if (idCategoria != null) {
                idCategoria = em.getReference(idCategoria.getClass(), idCategoria.getIdCategoria());
                producto.setIdCategoria(idCategoria);
            }
            Collection<DetalleVenta> attachedDetalleVentaCollection = new ArrayList<DetalleVenta>();
            for (DetalleVenta detalleVentaCollectionDetalleVentaToAttach : producto.getDetalleVentaCollection()) {
                detalleVentaCollectionDetalleVentaToAttach = em.getReference(detalleVentaCollectionDetalleVentaToAttach.getClass(), detalleVentaCollectionDetalleVentaToAttach.getIdDetalleVenta());
                attachedDetalleVentaCollection.add(detalleVentaCollectionDetalleVentaToAttach);
            }
            producto.setDetalleVentaCollection(attachedDetalleVentaCollection);
            Collection<DetalleCompra> attachedDetalleCompraCollection = new ArrayList<DetalleCompra>();
            for (DetalleCompra detalleCompraCollectionDetalleCompraToAttach : producto.getDetalleCompraCollection()) {
                detalleCompraCollectionDetalleCompraToAttach = em.getReference(detalleCompraCollectionDetalleCompraToAttach.getClass(), detalleCompraCollectionDetalleCompraToAttach.getIdDetalleCompra());
                attachedDetalleCompraCollection.add(detalleCompraCollectionDetalleCompraToAttach);
            }
            producto.setDetalleCompraCollection(attachedDetalleCompraCollection);
            Collection<DetalleFactura> attachedDetalleFacturaCollection = new ArrayList<DetalleFactura>();
            for (DetalleFactura detalleFacturaCollectionDetalleFacturaToAttach : producto.getDetalleFacturaCollection()) {
                detalleFacturaCollectionDetalleFacturaToAttach = em.getReference(detalleFacturaCollectionDetalleFacturaToAttach.getClass(), detalleFacturaCollectionDetalleFacturaToAttach.getIdDetalleFactura());
                attachedDetalleFacturaCollection.add(detalleFacturaCollectionDetalleFacturaToAttach);
            }
            producto.setDetalleFacturaCollection(attachedDetalleFacturaCollection);
            em.persist(producto);
            if (idCategoria != null) {
                idCategoria.getProductoCollection().add(producto);
                idCategoria = em.merge(idCategoria);
            }
            for (DetalleVenta detalleVentaCollectionDetalleVenta : producto.getDetalleVentaCollection()) {
                Producto oldIdProductoOfDetalleVentaCollectionDetalleVenta = detalleVentaCollectionDetalleVenta.getIdProducto();
                detalleVentaCollectionDetalleVenta.setIdProducto(producto);
                detalleVentaCollectionDetalleVenta = em.merge(detalleVentaCollectionDetalleVenta);
                if (oldIdProductoOfDetalleVentaCollectionDetalleVenta != null) {
                    oldIdProductoOfDetalleVentaCollectionDetalleVenta.getDetalleVentaCollection().remove(detalleVentaCollectionDetalleVenta);
                    oldIdProductoOfDetalleVentaCollectionDetalleVenta = em.merge(oldIdProductoOfDetalleVentaCollectionDetalleVenta);
                }
            }
            for (DetalleCompra detalleCompraCollectionDetalleCompra : producto.getDetalleCompraCollection()) {
                Producto oldIdProductoOfDetalleCompraCollectionDetalleCompra = detalleCompraCollectionDetalleCompra.getIdProducto();
                detalleCompraCollectionDetalleCompra.setIdProducto(producto);
                detalleCompraCollectionDetalleCompra = em.merge(detalleCompraCollectionDetalleCompra);
                if (oldIdProductoOfDetalleCompraCollectionDetalleCompra != null) {
                    oldIdProductoOfDetalleCompraCollectionDetalleCompra.getDetalleCompraCollection().remove(detalleCompraCollectionDetalleCompra);
                    oldIdProductoOfDetalleCompraCollectionDetalleCompra = em.merge(oldIdProductoOfDetalleCompraCollectionDetalleCompra);
                }
            }
            for (DetalleFactura detalleFacturaCollectionDetalleFactura : producto.getDetalleFacturaCollection()) {
                Producto oldIdProductoOfDetalleFacturaCollectionDetalleFactura = detalleFacturaCollectionDetalleFactura.getIdProducto();
                detalleFacturaCollectionDetalleFactura.setIdProducto(producto);
                detalleFacturaCollectionDetalleFactura = em.merge(detalleFacturaCollectionDetalleFactura);
                if (oldIdProductoOfDetalleFacturaCollectionDetalleFactura != null) {
                    oldIdProductoOfDetalleFacturaCollectionDetalleFactura.getDetalleFacturaCollection().remove(detalleFacturaCollectionDetalleFactura);
                    oldIdProductoOfDetalleFacturaCollectionDetalleFactura = em.merge(oldIdProductoOfDetalleFacturaCollectionDetalleFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getIdProducto());
            Categoria idCategoriaOld = persistentProducto.getIdCategoria();
            Categoria idCategoriaNew = producto.getIdCategoria();
            Collection<DetalleVenta> detalleVentaCollectionOld = persistentProducto.getDetalleVentaCollection();
            Collection<DetalleVenta> detalleVentaCollectionNew = producto.getDetalleVentaCollection();
            Collection<DetalleCompra> detalleCompraCollectionOld = persistentProducto.getDetalleCompraCollection();
            Collection<DetalleCompra> detalleCompraCollectionNew = producto.getDetalleCompraCollection();
            Collection<DetalleFactura> detalleFacturaCollectionOld = persistentProducto.getDetalleFacturaCollection();
            Collection<DetalleFactura> detalleFacturaCollectionNew = producto.getDetalleFacturaCollection();
            List<String> illegalOrphanMessages = null;
            for (DetalleVenta detalleVentaCollectionOldDetalleVenta : detalleVentaCollectionOld) {
                if (!detalleVentaCollectionNew.contains(detalleVentaCollectionOldDetalleVenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleVenta " + detalleVentaCollectionOldDetalleVenta + " since its idProducto field is not nullable.");
                }
            }
            for (DetalleCompra detalleCompraCollectionOldDetalleCompra : detalleCompraCollectionOld) {
                if (!detalleCompraCollectionNew.contains(detalleCompraCollectionOldDetalleCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleCompra " + detalleCompraCollectionOldDetalleCompra + " since its idProducto field is not nullable.");
                }
            }
            for (DetalleFactura detalleFacturaCollectionOldDetalleFactura : detalleFacturaCollectionOld) {
                if (!detalleFacturaCollectionNew.contains(detalleFacturaCollectionOldDetalleFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleFactura " + detalleFacturaCollectionOldDetalleFactura + " since its idProducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idCategoriaNew != null) {
                idCategoriaNew = em.getReference(idCategoriaNew.getClass(), idCategoriaNew.getIdCategoria());
                producto.setIdCategoria(idCategoriaNew);
            }
            Collection<DetalleVenta> attachedDetalleVentaCollectionNew = new ArrayList<DetalleVenta>();
            for (DetalleVenta detalleVentaCollectionNewDetalleVentaToAttach : detalleVentaCollectionNew) {
                detalleVentaCollectionNewDetalleVentaToAttach = em.getReference(detalleVentaCollectionNewDetalleVentaToAttach.getClass(), detalleVentaCollectionNewDetalleVentaToAttach.getIdDetalleVenta());
                attachedDetalleVentaCollectionNew.add(detalleVentaCollectionNewDetalleVentaToAttach);
            }
            detalleVentaCollectionNew = attachedDetalleVentaCollectionNew;
            producto.setDetalleVentaCollection(detalleVentaCollectionNew);
            Collection<DetalleCompra> attachedDetalleCompraCollectionNew = new ArrayList<DetalleCompra>();
            for (DetalleCompra detalleCompraCollectionNewDetalleCompraToAttach : detalleCompraCollectionNew) {
                detalleCompraCollectionNewDetalleCompraToAttach = em.getReference(detalleCompraCollectionNewDetalleCompraToAttach.getClass(), detalleCompraCollectionNewDetalleCompraToAttach.getIdDetalleCompra());
                attachedDetalleCompraCollectionNew.add(detalleCompraCollectionNewDetalleCompraToAttach);
            }
            detalleCompraCollectionNew = attachedDetalleCompraCollectionNew;
            producto.setDetalleCompraCollection(detalleCompraCollectionNew);
            Collection<DetalleFactura> attachedDetalleFacturaCollectionNew = new ArrayList<DetalleFactura>();
            for (DetalleFactura detalleFacturaCollectionNewDetalleFacturaToAttach : detalleFacturaCollectionNew) {
                detalleFacturaCollectionNewDetalleFacturaToAttach = em.getReference(detalleFacturaCollectionNewDetalleFacturaToAttach.getClass(), detalleFacturaCollectionNewDetalleFacturaToAttach.getIdDetalleFactura());
                attachedDetalleFacturaCollectionNew.add(detalleFacturaCollectionNewDetalleFacturaToAttach);
            }
            detalleFacturaCollectionNew = attachedDetalleFacturaCollectionNew;
            producto.setDetalleFacturaCollection(detalleFacturaCollectionNew);
            producto = em.merge(producto);
            if (idCategoriaOld != null && !idCategoriaOld.equals(idCategoriaNew)) {
                idCategoriaOld.getProductoCollection().remove(producto);
                idCategoriaOld = em.merge(idCategoriaOld);
            }
            if (idCategoriaNew != null && !idCategoriaNew.equals(idCategoriaOld)) {
                idCategoriaNew.getProductoCollection().add(producto);
                idCategoriaNew = em.merge(idCategoriaNew);
            }
            for (DetalleVenta detalleVentaCollectionNewDetalleVenta : detalleVentaCollectionNew) {
                if (!detalleVentaCollectionOld.contains(detalleVentaCollectionNewDetalleVenta)) {
                    Producto oldIdProductoOfDetalleVentaCollectionNewDetalleVenta = detalleVentaCollectionNewDetalleVenta.getIdProducto();
                    detalleVentaCollectionNewDetalleVenta.setIdProducto(producto);
                    detalleVentaCollectionNewDetalleVenta = em.merge(detalleVentaCollectionNewDetalleVenta);
                    if (oldIdProductoOfDetalleVentaCollectionNewDetalleVenta != null && !oldIdProductoOfDetalleVentaCollectionNewDetalleVenta.equals(producto)) {
                        oldIdProductoOfDetalleVentaCollectionNewDetalleVenta.getDetalleVentaCollection().remove(detalleVentaCollectionNewDetalleVenta);
                        oldIdProductoOfDetalleVentaCollectionNewDetalleVenta = em.merge(oldIdProductoOfDetalleVentaCollectionNewDetalleVenta);
                    }
                }
            }
            for (DetalleCompra detalleCompraCollectionNewDetalleCompra : detalleCompraCollectionNew) {
                if (!detalleCompraCollectionOld.contains(detalleCompraCollectionNewDetalleCompra)) {
                    Producto oldIdProductoOfDetalleCompraCollectionNewDetalleCompra = detalleCompraCollectionNewDetalleCompra.getIdProducto();
                    detalleCompraCollectionNewDetalleCompra.setIdProducto(producto);
                    detalleCompraCollectionNewDetalleCompra = em.merge(detalleCompraCollectionNewDetalleCompra);
                    if (oldIdProductoOfDetalleCompraCollectionNewDetalleCompra != null && !oldIdProductoOfDetalleCompraCollectionNewDetalleCompra.equals(producto)) {
                        oldIdProductoOfDetalleCompraCollectionNewDetalleCompra.getDetalleCompraCollection().remove(detalleCompraCollectionNewDetalleCompra);
                        oldIdProductoOfDetalleCompraCollectionNewDetalleCompra = em.merge(oldIdProductoOfDetalleCompraCollectionNewDetalleCompra);
                    }
                }
            }
            for (DetalleFactura detalleFacturaCollectionNewDetalleFactura : detalleFacturaCollectionNew) {
                if (!detalleFacturaCollectionOld.contains(detalleFacturaCollectionNewDetalleFactura)) {
                    Producto oldIdProductoOfDetalleFacturaCollectionNewDetalleFactura = detalleFacturaCollectionNewDetalleFactura.getIdProducto();
                    detalleFacturaCollectionNewDetalleFactura.setIdProducto(producto);
                    detalleFacturaCollectionNewDetalleFactura = em.merge(detalleFacturaCollectionNewDetalleFactura);
                    if (oldIdProductoOfDetalleFacturaCollectionNewDetalleFactura != null && !oldIdProductoOfDetalleFacturaCollectionNewDetalleFactura.equals(producto)) {
                        oldIdProductoOfDetalleFacturaCollectionNewDetalleFactura.getDetalleFacturaCollection().remove(detalleFacturaCollectionNewDetalleFactura);
                        oldIdProductoOfDetalleFacturaCollectionNewDetalleFactura = em.merge(oldIdProductoOfDetalleFacturaCollectionNewDetalleFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getIdProducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getIdProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DetalleVenta> detalleVentaCollectionOrphanCheck = producto.getDetalleVentaCollection();
            for (DetalleVenta detalleVentaCollectionOrphanCheckDetalleVenta : detalleVentaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the DetalleVenta " + detalleVentaCollectionOrphanCheckDetalleVenta + " in its detalleVentaCollection field has a non-nullable idProducto field.");
            }
            Collection<DetalleCompra> detalleCompraCollectionOrphanCheck = producto.getDetalleCompraCollection();
            for (DetalleCompra detalleCompraCollectionOrphanCheckDetalleCompra : detalleCompraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the DetalleCompra " + detalleCompraCollectionOrphanCheckDetalleCompra + " in its detalleCompraCollection field has a non-nullable idProducto field.");
            }
            Collection<DetalleFactura> detalleFacturaCollectionOrphanCheck = producto.getDetalleFacturaCollection();
            for (DetalleFactura detalleFacturaCollectionOrphanCheckDetalleFactura : detalleFacturaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the DetalleFactura " + detalleFacturaCollectionOrphanCheckDetalleFactura + " in its detalleFacturaCollection field has a non-nullable idProducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Categoria idCategoria = producto.getIdCategoria();
            if (idCategoria != null) {
                idCategoria.getProductoCollection().remove(producto);
                idCategoria = em.merge(idCategoria);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
