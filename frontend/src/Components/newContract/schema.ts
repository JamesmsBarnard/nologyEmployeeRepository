import { z } from "zod";

export const schema = z.object({
    employeeId:     z.coerce.number().min(1),
    contractTypeId: z.coerce.number().min(1),
    startDate:      z.string(),
    endDate:        z.string(),
});

export type ContractFormData = z.infer<typeof schema>;