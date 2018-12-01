namespace cattocdi.entity
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("Review")]
    public partial class Review
    {
        public int Id { get; set; }

        public int AppointmentId { get; set; }

        public DateTime Date { get; set; }

        public byte RateNumber { get; set; }

        public string Comment { get; set; }

        public virtual Appointment Appointment { get; set; }
    }
}
