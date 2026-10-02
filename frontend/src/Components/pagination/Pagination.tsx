interface PaginationProps{
    currentPage: number;
    totalPages: number;
    searchParam: string | null;
    onPageChange: (Page: number) => unknown;
    onSearch: (Search: string | undefined) => unknown;
}

export default function Pagination({
    currentPage,
    totalPages,
    onPageChange,
    onSearch,
}: PaginationProps) {
    return(
        <div>
            <br/>
            <div>
                <input id="searchValue"></input>
                <button onClick={() => onSearch((document.getElementById("searchValue") as HTMLInputElement).value)}>
                    search
                </button>
            </div>
            <button
                disabled={currentPage === 1}
                onClick={() => onPageChange(currentPage - 1)}
            >
                previous
            </button>

            <span>
                {currentPage} of {totalPages}
            </span>

            <button
                disabled={currentPage === totalPages}
                onClick={() => onPageChange(currentPage + 1)}
            >
                next
            </button>
        </div>
    )
}