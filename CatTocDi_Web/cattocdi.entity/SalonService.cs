namespace cattocdi.entity
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("SalonService")]
    public partial class SalonService
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public SalonService()
        {
            ServiceAppointments = new HashSet<ServiceAppointment>();
        }

        public int Id { get; set; }

        public int SalonId { get; set; }

        public int ServiceId { get; set; }

        public double? Price { get; set; }

        public int? AvarageTime { get; set; }

        public bool Disabled { get; set; }

        public virtual Salon Salon { get; set; }

        public virtual Service Service { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<ServiceAppointment> ServiceAppointments { get; set; }
    }
}
