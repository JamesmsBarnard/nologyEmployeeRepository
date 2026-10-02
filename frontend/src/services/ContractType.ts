const BASE_URL = import.meta.env.VITE_API_URL;


export interface ContractTypes
{
    id: number;
    type: string;
}

export async function getAllTypes() {
    const response = await fetch(BASE_URL + "/contractType");

    if(!response.ok)
    {
        throw new Error("Could not fetch contract types");
    }

    return (await response.json()) as ContractTypes[];    
}

export async function getTypeById(id: number) {
        const response = await fetch(BASE_URL + "/contractType/" + id);

        if(!response.ok)
        {
            throw new Error("Could not fetch contract types");
        }

        return (await response.json()) as ContractTypes;  
}