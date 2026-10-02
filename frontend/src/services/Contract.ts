const BASE_URL = import.meta.env.VITE_API_URL;

interface Contract {
    id: number;
    employeeId: number;
    contractTypeId: number;
    startDate: string;
    endDate: string;
}

interface CreateContractDTO
{
    employeeId: number;
    contractTypeId: number;
    startDate: string;
    endDate: string;
}

export async function getAllContracts() {
    const response = await fetch(BASE_URL + "/contracts");

    if(!response.ok)
    {
        throw new Error("Could not fetch contracts");
    }

    return (await response.json() as Contract[]);
}

export async function getContractsByEmployee(id: number)
{
    const response = await fetch(BASE_URL + "/contracts/employee/" + id);

    if(!response.ok)
    {
        throw new Error("Could not fetch contracts");
    }

    return (await response.json() as Contract[]);
}


export async function createContract(contractData: CreateContractDTO)
{
    console.log("Contract Data:")
    console.log(contractData);

    const response = await fetch(BASE_URL + "/contracts", {
        method: 'POST',
        body: JSON.stringify(contractData),
        headers: { 'Content-Type': 'application/json' },
    })

    if(!response.ok)
    {
        throw new Error('Could not add contract');
    }

    return (await response.json()) as Contract;
}
