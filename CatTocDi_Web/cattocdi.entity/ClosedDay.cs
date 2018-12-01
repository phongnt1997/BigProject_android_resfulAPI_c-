namespace cattocdi.entity
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("ClosedDay")]
    public partial class ClosedDay
    {
        public int Id { get; set; }

        [Column(TypeName = "date")]
        public DateTime? Date { get; set; }

        public string Description { get; set; }

        public int SalonId { get; set; }

        public virtual Salon Salon { get; set; }
    }
}
