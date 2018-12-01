namespace cattocdi.entity
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("TimeSlot")]
    public partial class TimeSlot
    {
        public int Id { get; set; }

        public int? SalonId { get; set; }

        [Column(TypeName = "date")]
        public DateTime? SlotDate { get; set; }

        public TimeSpan? SlotTime { get; set; }

        public int? DayOfWeek { get; set; }

        public bool? SlotType { get; set; }

        public byte? Status { get; set; }
    }
}
