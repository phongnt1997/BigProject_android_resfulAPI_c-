namespace cattocdi.entity
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("WorkingHour")]
    public partial class WorkingHour
    {
        public int Id { get; set; }

        public int SalonId { get; set; }

        public byte DayOfWeek { get; set; }

        public TimeSpan StartHour { get; set; }

        public TimeSpan EndHour { get; set; }

        public bool IsClosed { get; set; }

        public virtual Salon Salon { get; set; }
    }
}
