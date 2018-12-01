using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.Service.ViewModel.User
{
    public class SlotTimeViewModel
    {
        public int Id { get; set; }
        public int SalonId { get; set; }
        public DateTime SlotDate { get; set; }
        public TimeSpan Time { get; set; }
        public int Capacity { get; set; }
        public int Index { get; set; }
    }
}
