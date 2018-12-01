using cattocdi.entity;
using cattocdi.repository;
using cattocdi.Service.Interface;
using cattocdi.Service.ViewModel.User;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.Service.Implement
{
    public class CustomerService : ICustomerService
    {
        private IRepository<Customer> _customerRepo;
        private IUnitOfWork _unitOfWork;
        public CustomerService(IRepository<Customer> customerRepo, IUnitOfWork unitOfWork)
        {
            _customerRepo = customerRepo;
            _unitOfWork = unitOfWork;
        }
        public void CreateCustomerAccount(CustomerViewModel model)
        {
            if (model != null)
            {
                Customer newCustomer = new Customer
                {
                    FirstName = model.FirstName,
                    LastName = model.LastName,
                    Gender = model.Gender,
                    AccountId = model.AccountId,
                    Email = model.Email,
                    Phone = model.Phone                    
                };
               _customerRepo.Insert(newCustomer);
               int n = _unitOfWork.SaveChanges();             
            }
        }

        public ProfileViewModel GetCustomerProfile(string accountId)
        {
            var user = _customerRepo.Gets()
                .Where(x => x.AccountId.Equals(accountId))
                .Select(z => new ProfileViewModel
                {
                    CustomerID = z.CustomerId,
                    Email = z.Email,
                    AccountId = z.AccountId,
                    Firstname = z.FirstName,
                    Gender = z.Gender ?? false,
                    Lastname = z.LastName,
                    Phone = z.Phone
                }).FirstOrDefault();
            return user;
        }

        public bool UpdateCustomerProfile(ProfileViewModel model)
        {
            var customer = _customerRepo.Gets().Where(s => s.AccountId == model.AccountId).FirstOrDefault();

            customer.FirstName = model.Firstname;
            customer.LastName = model.Lastname;
            customer.Email = model.Email;
            _customerRepo.Edit(customer);
            return _unitOfWork.SaveChanges() > 0 ? true : false;

        }
    }
}
