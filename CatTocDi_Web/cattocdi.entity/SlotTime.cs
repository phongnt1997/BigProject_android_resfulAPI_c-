namespace cattocdi.entity
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("SlotTime")]
    public partial class SlotTime
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public SlotTime()
        {
            SlotAppointments = new HashSet<SlotAppointment>();
        }

        public int Id { get; set; }

        public int SalonId { get; set; }

        public DateTime SlotDate { get; set; }

        public byte? Slot1 { get; set; }

        public byte? Slot2 { get; set; }

        public byte? Slot3 { get; set; }

        public byte? Slot4 { get; set; }

        public byte? Slot5 { get; set; }

        public byte? Slot6 { get; set; }

        public byte? Slot7 { get; set; }

        public byte? Slot8 { get; set; }

        public byte? Slot9 { get; set; }

        public byte? Slot10 { get; set; }

        public byte? Slot11 { get; set; }

        public byte? Slot12 { get; set; }

        public byte? Slot13 { get; set; }

        public byte? Slot14 { get; set; }

        public byte? Slot15 { get; set; }

        public byte? Slot16 { get; set; }

        public byte? Slot17 { get; set; }

        public byte? Slot18 { get; set; }

        public byte? Slot19 { get; set; }

        public byte? Slot20 { get; set; }

        public byte? Slot21 { get; set; }

        public byte? Slot22 { get; set; }

        public byte? Slot23 { get; set; }

        public byte? Slot24 { get; set; }

        public byte? Slot25 { get; set; }

        public byte? Slot26 { get; set; }

        public byte? Slot27 { get; set; }

        public byte? Slot28 { get; set; }

        public byte? Slot29 { get; set; }

        public byte? Slot30 { get; set; }

        public byte? Slot31 { get; set; }

        public byte? Slot32 { get; set; }

        public byte? Slot33 { get; set; }

        public byte? Slot34 { get; set; }

        public byte? Slot35 { get; set; }

        public byte? Slot36 { get; set; }

        public byte? Slot37 { get; set; }

        public byte? Slot38 { get; set; }

        public byte? Slot39 { get; set; }

        public byte? Slot40 { get; set; }

        public byte? Slot41 { get; set; }

        public byte? Slot42 { get; set; }

        public byte? Slot43 { get; set; }

        public byte? Slot44 { get; set; }

        public byte? Slot45 { get; set; }

        public byte? Slot46 { get; set; }

        public byte? Slot47 { get; set; }

        public byte? Slot48 { get; set; }

        public byte? Slot49 { get; set; }

        public byte? Slot50 { get; set; }

        public byte? Slot51 { get; set; }

        public byte? Slot52 { get; set; }

        public byte? Slot53 { get; set; }

        public byte? Slot54 { get; set; }

        public byte? Slot55 { get; set; }

        public byte? Slot56 { get; set; }

        public byte? Slot57 { get; set; }

        public byte? Slot58 { get; set; }

        public byte? Slot59 { get; set; }

        public byte? Slot60 { get; set; }

        public byte? Slot61 { get; set; }

        public byte? Slot62 { get; set; }

        public byte? Slot63 { get; set; }

        public byte? Slot64 { get; set; }

        public byte? Slot65 { get; set; }

        public byte? Slot66 { get; set; }

        public byte? Slot67 { get; set; }

        public byte? Slot68 { get; set; }

        public byte? Slot69 { get; set; }

        public byte? Slot70 { get; set; }

        public byte? Slot71 { get; set; }

        public byte? Slot72 { get; set; }

        public byte? Slot73 { get; set; }

        public byte? Slot74 { get; set; }

        public byte? Slot75 { get; set; }

        public byte? Slot76 { get; set; }

        public byte? Slot77 { get; set; }

        public byte? Slot78 { get; set; }

        public byte? Slot79 { get; set; }

        public byte? Slot80 { get; set; }

        public byte? Slot81 { get; set; }

        public byte? Slot82 { get; set; }

        public byte? Slot83 { get; set; }

        public byte? Slot84 { get; set; }

        public byte? Slot85 { get; set; }

        public byte? Slot86 { get; set; }

        public byte? Slot87 { get; set; }

        public byte? Slot88 { get; set; }

        public byte? Slot89 { get; set; }

        public byte? Slot90 { get; set; }

        public byte? Slot91 { get; set; }

        public byte? Slot92 { get; set; }

        public byte? Slot93 { get; set; }

        public byte? Slot94 { get; set; }

        public byte? Slot95 { get; set; }

        public byte? Slot96 { get; set; }

        public virtual Salon Salon { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<SlotAppointment> SlotAppointments { get; set; }
    }
}
