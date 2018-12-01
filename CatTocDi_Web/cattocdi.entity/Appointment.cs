namespace cattocdi.entity
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("Appointment")]
    public partial class Appointment
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public Appointment()
        {
            Reviews = new HashSet<Review>();
            ServiceAppointments = new HashSet<ServiceAppointment>();
            SlotAppointments = new HashSet<SlotAppointment>();
        }

        public int Id { get; set; }

        public int CustomerId { get; set; }

        public int Status { get; set; }

        [Column(TypeName = "date")]
        public DateTime BookedDate { get; set; }

        public int Duration { get; set; }

        public int? PromotionId { get; set; }

        public DateTime StartTime { get; set; }

        public virtual Customer Customer { get; set; }

        public virtual Promotion Promotion { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Review> Reviews { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<ServiceAppointment> ServiceAppointments { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<SlotAppointment> SlotAppointments { get; set; }
    }
}
