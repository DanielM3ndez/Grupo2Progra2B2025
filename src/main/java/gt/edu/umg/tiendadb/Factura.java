/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.tiendadb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "Factura", catalog = "TiendaDB", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"numeroDocumento"}),
    @UniqueConstraint(columnNames = {"idVenta"})})
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f"),
    @NamedQuery(name = "Factura.findByIdFactura", query = "SELECT f FROM Factura f WHERE f.idFactura = :idFactura"),
    @NamedQuery(name = "Factura.findByTipoDocumento", query = "SELECT f FROM Factura f WHERE f.tipoDocumento = :tipoDocumento"),
    @NamedQuery(name = "Factura.findByNumeroDocumento", query = "SELECT f FROM Factura f WHERE f.numeroDocumento = :numeroDocumento"),
    @NamedQuery(name = "Factura.findByNombreCliente", query = "SELECT f FROM Factura f WHERE f.nombreCliente = :nombreCliente"),
    @NamedQuery(name = "Factura.findByDocumentoCliente", query = "SELECT f FROM Factura f WHERE f.documentoCliente = :documentoCliente"),
    @NamedQuery(name = "Factura.findByMontoPago", query = "SELECT f FROM Factura f WHERE f.montoPago = :montoPago"),
    @NamedQuery(name = "Factura.findByMontoCambio", query = "SELECT f FROM Factura f WHERE f.montoCambio = :montoCambio"),
    @NamedQuery(name = "Factura.findByMontoTotal", query = "SELECT f FROM Factura f WHERE f.montoTotal = :montoTotal"),
    @NamedQuery(name = "Factura.findByFechaRegistro", query = "SELECT f FROM Factura f WHERE f.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Factura.findByEstado", query = "SELECT f FROM Factura f WHERE f.estado = :estado")})
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFactura", nullable = false)
    private Integer idFactura;
    @Basic(optional = false)
    @Column(name = "tipoDocumento", nullable = false, length = 20)
    private String tipoDocumento;
    @Basic(optional = false)
    @Column(name = "numeroDocumento", nullable = false, length = 20)
    private String numeroDocumento;
    @Basic(optional = false)
    @Column(name = "nombreCliente", nullable = false, length = 100)
    private String nombreCliente;
    @Basic(optional = false)
    @Column(name = "documentoCliente", nullable = false, length = 20)
    private String documentoCliente;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "montoPago", nullable = false, precision = 18, scale = 2)
    private BigDecimal montoPago;
    @Basic(optional = false)
    @Column(name = "montoCambio", nullable = false, precision = 18, scale = 2)
    private BigDecimal montoCambio;
    @Basic(optional = false)
    @Column(name = "montoTotal", nullable = false, precision = 18, scale = 2)
    private BigDecimal montoTotal;
    @Column(name = "fechaRegistro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "estado", length = 20)
    private String estado;
    @Lob
    @Column(name = "observaciones", length = 2147483647)
    private String observaciones;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFactura", fetch = FetchType.LAZY)
    private Collection<DetalleFactura> detalleFacturaCollection;
    @JoinColumn(name = "idVenta", referencedColumnName = "idVenta", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Venta idVenta;

    public Factura() {
    }

    public Factura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public Factura(Integer idFactura, String tipoDocumento, String numeroDocumento, String nombreCliente, String documentoCliente, BigDecimal montoPago, BigDecimal montoCambio, BigDecimal montoTotal) {
        this.idFactura = idFactura;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombreCliente = nombreCliente;
        this.documentoCliente = documentoCliente;
        this.montoPago = montoPago;
        this.montoCambio = montoCambio;
        this.montoTotal = montoTotal;
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDocumentoCliente() {
        return documentoCliente;
    }

    public void setDocumentoCliente(String documentoCliente) {
        this.documentoCliente = documentoCliente;
    }

    public BigDecimal getMontoPago() {
        return montoPago;
    }

    public void setMontoPago(BigDecimal montoPago) {
        this.montoPago = montoPago;
    }

    public BigDecimal getMontoCambio() {
        return montoCambio;
    }

    public void setMontoCambio(BigDecimal montoCambio) {
        this.montoCambio = montoCambio;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Collection<DetalleFactura> getDetalleFacturaCollection() {
        return detalleFacturaCollection;
    }

    public void setDetalleFacturaCollection(Collection<DetalleFactura> detalleFacturaCollection) {
        this.detalleFacturaCollection = detalleFacturaCollection;
    }

    public Venta getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Venta idVenta) {
        this.idVenta = idVenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFactura != null ? idFactura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.idFactura == null && other.idFactura != null) || (this.idFactura != null && !this.idFactura.equals(other.idFactura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.umg.tiendadb.Factura[ idFactura=" + idFactura + " ]";
    }
    
}
