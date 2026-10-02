import { useEffect, useState } from 'react'

import "./App.css"

//employee
import Employee from "./Components/Employee/Employee";
import { createEmployee, getAllEmployees } from './services/Employees';
import { getAllTypes, type ContractTypes } from './services/ContractType';
import type { EmployeeFormData } from './Components/NewEmployee/schema';
import NewEmployeeFrom from './Components/NewEmployee/NewEmployee';
import type { PageResponse } from './types/pagination';
import Pagination from './Components/pagination/Pagination';



function App() {
    const [employeePageData, setEmployeePageData] = useState<PageResponse<Employee> | null >(
      null,
    );
    
    const [ types,       setTypes  ] = useState<ContractTypes[]>([]); 
    const [ page,        setPage   ] = useState<number>();
    const [ searchParam, setSearch ] = useState<string>();

    useEffect(() => {
      getAllEmployees(page, 5, searchParam).then(setEmployeePageData);
    }, [page, searchParam]);

     useEffect(() => {
      getAllTypes().then(setTypes);
    }, []);

    const onSubmit = async (data: EmployeeFormData) => {
      //const createdEmployee = 
      await createEmployee(data);
      //setEmployees([...employees, createdEmployee]);
    };

    document.title="Employee Database";
    
   return (
    <>
      <h1>Employee App</h1>

      <NewEmployeeFrom types={types} onSubmit={onSubmit}/>

      {/* {employees.map((employeeFromApi) => {
        return <Employee employee={employeeFromApi} key={employeeFromApi.id}/>
      })} */}

      {employeePageData && (
        <Pagination
           currentPage={employeePageData.currentPage}
           totalPages={employeePageData.totalPages}
           searchParam={employeePageData.searchParam}
           onPageChange={(newPage) => setPage(newPage)} 
           onSearch={(newSearch) => setSearch(newSearch)}
          />
      )}

      {employeePageData?.data.map((employeeFromApi) => {
        return<Employee employee={employeeFromApi} key={employeeFromApi.id} />;
      })}


    </>
  )
}

export default App
