import type { PageResponse } from "../types/pagination";

const BASE_URL = import.meta.env.VITE_API_URL;

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

interface CreateEmployeeDTO
{
    firstName: string;
    middleName: string;
    lastName: string;
    email: string;
    phoneNumber: string;
    address: string;
    // typeId: number;
    // startDate: string;
    // endDate: string;
}

export async function getAllEmployees(page = 1, size = 5, searchParam="") {
    const reponse = await fetch( 
        BASE_URL + "/employees?page=" + page + "&size=" + size + "&search=" + searchParam
    );

    //console.log();

    if(!reponse.ok)
    {
        throw new Error("Could not fetch employees");
    }

    return (await reponse.json() as PageResponse<Employee>)
}

export async function createEmployee(employeeData : CreateEmployeeDTO)
{
    console.log("Hello");
    console.log(employeeData);

    const response = await fetch(BASE_URL + "/employees", {
        method: 'POST',
        body: JSON.stringify(employeeData),
        headers: { 'Content-Type': 'application/json' },
    });

    if (!response.ok)
    {
        throw new Error('Could not add employee');
    }

    return (await response.json()) as Employee;
}