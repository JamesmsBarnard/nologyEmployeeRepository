interface Contract {
    id: number;
    employeeId: number;
    contractTypeId: number;
    startDate: string;
    endDate: string;
}

interface ContractProps{
    contract: Contract;
}

function Contract({contract}: ContractProps)
{
    return(
        <article>
            {Date.parse(contract.endDate) > Date.now() || contract.endDate == null?<p style={{color:"green", fontWeight:"bold"}}>Active</p>:<p style={{color:"red", fontWeight:"bold"}}>Inactive</p>}
            <p>{contract.contractTypeId}</p>
            <p>From: {contract.startDate}</p>
            {contract.endDate != null ? <p>To: {contract.endDate}</p>:null}
            <br/>
        </article>
    );
}

export default Contract;