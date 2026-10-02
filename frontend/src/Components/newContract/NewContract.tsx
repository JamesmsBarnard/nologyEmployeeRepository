import { useForm } from "react-hook-form";
import type { ContractTypes } from "../../services/ContractType";
import { schema, type ContractFormData } from "./schema";
import { zodResolver } from "@hookform/resolvers/zod";

interface FormProps
{
    contractTypes: ContractTypes[];
    employeeId: number;
    onSubmit: (data: ContractFormData) => unknown;
}

function NewContractForm({ contractTypes, employeeId, onSubmit }: FormProps)
{
    const
    {
        handleSubmit,
        formState: {errors, isSubmitSuccessful},
        register,
        reset,
    } = useForm({
        resolver: zodResolver(schema),
    })

    isSubmitSuccessful && reset();

    return(
        <form onSubmit={handleSubmit(onSubmit)}>
            <input type="hidden" value={employeeId} {...register("employeeId")}/>
            <div>
                <label htmlFor="">Contract type: </label>
                <select id="" {...register("contractTypeId")}>
                    <option disabled value="">
                        Select Contract Type...
                    </option>
                    {contractTypes &&
                        contractTypes.map((type) => {
                            return (
                                <option key={type.id} value={type.id}>
                                    {type.type}
                                </option>
                            );
                        })
                    }
                </select>
            </div>   
            <div>
                <label htmlFor="">Start Date: </label>
                <input type="date" {...register("startDate")}/>
                {errors?.startDate && (
                    <small style={{color: "red"}}>
                        {errors.startDate?.message}
                    </small>
                )}
            </div>
            <div>
                <label htmlFor="">End Date: </label>
                <input type="date" {...register("endDate")}/>
                {errors?.endDate && (
                    <small style={{color: "red"}}>
                        {errors.endDate?.message}
                    </small>
                )}
            </div>
            <button>Add Contract</button>
        </form>
    )
}

export default NewContractForm;