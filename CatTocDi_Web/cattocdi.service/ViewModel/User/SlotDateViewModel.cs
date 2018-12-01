using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.Service.ViewModel.User
{
    public class SlotDateViewModel
    {
        public DateTime date { get; set; }
        public List<SlotTimeViewModel> slots { get; set; }
    }
}
