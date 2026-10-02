import { useEffect, useState } from 'react'

import Contract from "../Contract/Contract";
import {createContract, getContractsByEmployee} from '../../services/Contract';

import { getAllTypes, type ContractTypes } from '../../services/ContractType';

import type { ContractFormData } from '../newContract/schema';
import NewContractForm from '../newContract/NewContract';


interface Employee {
    id: number;
    firstName: string;
    middleName: string;
    lastName: string;
    email: string;
    phoneNumber: string;
    address: string;
    // typeId: string;
    // startDate: string;
    // endDate: string;
}

interface EmployeeProps{
    employee: Employee;
}

function toggleContent(id: number){
    console.log("press");
    var form = document.getElementById('contractForm' + id);

    if(form != null)
    {
        console.log(form.style.height);   
        form.style.height != "0px"? form.style.height = "0":form.style.height = "";
    }
}

function Employee({employee}: EmployeeProps)
{
    const [ contracts, setContracts ] = useState<Contract[]>([]);
    const [ types, setTypes ] = useState<ContractTypes[]>([]);

    const onSubmit = async (data: ContractFormData) => {
        const createdContract = await createContract(data);
        setContracts([...contracts, createdContract]);
    }

    useEffect(() => {
        getContractsByEmployee(employee.id).then(setContracts)
        getAllTypes().then(setTypes)
    }, []);

    return(
        <article>
            <h3>
                {employee.firstName}{employee.middleName != null ? " " + employee.middleName : ""} {employee.lastName} 
            </h3>
            <p>{employee.phoneNumber} {employee.email}</p>
            <p>{employee.address}</p>
            <p>Contracts: </p>
            {contracts.map((contractsFromApi) => {
                return <Contract contract={contractsFromApi} key={contractsFromApi.id}/>
            })}
            <button onClick={() => toggleContent(employee.id)}>Add Contract</button>
            <div id={'contractForm' + employee.id} style={{overflow:"hidden", height:"0"}}>
                <NewContractForm contractTypes={types} employeeId={employee.id} onSubmit={onSubmit}/>
            </div>
        </article>
    );
}

export default Employee;