namespace cattocdi.entity
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("SlotAppointment")]
    public partial class SlotAppointment
    {
        public int Id { get; set; }

        public int? SlotId { get; set; }

        public int? AppointmentId { get; set; }

        public virtual Appointment Appointment { get; set; }

        public virtual SlotTime SlotTime { get; set; }
    }
}
