using System;
using System.Collections.Generic;

namespace cattocdi.Service.ViewModel.User
{
   public class NewAppointmentViewModel
    {
        public string AccountId { get; set; }
        public int CustomerId { get; set; }
        public int SalonId { get; set; }
        public List<int> Indexes { get; set; }
        public int SlotId { get; set; }
        public int Duration { get; set; }
        public List<int> Services { get; set; }
        public string StartTime { get; set; }
        public int? PromotionId { get; set; }        
    }
}
