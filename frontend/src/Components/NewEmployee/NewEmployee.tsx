import { useForm } from "react-hook-form";
import type { ContractTypes } from "../../services/ContractType";
import { schema , type EmployeeFormData } from "./schema";
import {zodResolver} from "@hookform/resolvers/zod";

interface FormProps 
{
    types: ContractTypes[];
    onSubmit: (data: EmployeeFormData) => unknown; 
}

function NewEmployeeFrom({ onSubmit }: FormProps)
{
    const {
        handleSubmit,
        formState: {errors, isSubmitSuccessful},
        register,
        reset,
    } = useForm({
        resolver: zodResolver(schema),
    });

    isSubmitSuccessful && reset();

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <div>
                <label htmlFor="">First Name: </label>
                <input type="text" {...register("firstName")}/>
                {errors?.firstName && (
                    <small style={{color: "red"}}>
                        {errors.firstName?.message}
                    </small>
                )}
            </div>
            <div>
                <label htmlFor="">Middle Name: </label>
                <input type="text" {...register("middleName")}/>
                {errors?.middleName && (
                    <small style={{color: "red"}}>
                        {errors.middleName?.message}
                    </small>
                )}
            </div>
            <div>
                <label htmlFor="">Last Name: </label>
                <input type="text" {...register("lastName")}/>
                {errors?.lastName && (
                    <small style={{color: "red"}}>
                        {errors.lastName?.message}
                    </small>
                )}
            </div>
            <div>
                <label htmlFor="">Email: </label>
                <input type="text" {...register("email")}/>
                {errors?.email && (
                    <small style={{color: "red"}}>
                        {errors.email?.message}
                    </small>
                )}
            </div>
            <div>
                <label htmlFor="">Phone Number: </label>
                <input type="text" {...register("phoneNumber")}/>
                {errors?.phoneNumber && (
                    <small style={{color: "red"}}>
                        {errors.phoneNumber?.message}
                    </small>
                )}
            </div>
            <div>
                <label htmlFor="">Address: </label>
                <input type="text" {...register("address")}/>
                {errors?.address && (
                    <small style={{color: "red"}}>
                        {errors.address?.message}
                    </small>
                )}
            </div>

            {/* <div>
                <label htmlFor="">Contract type: </label>
                <select id="" {...register("typeId")}>
                    <option disabled value="">
                        Select Contract Type...
                    </option>
                    {types &&
                        types.map((type) => {
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
            </div> */}

            <button>Add Employee</button>
        </form>
    )
}

export default NewEmployeeFrom;