import GeneralInformationForm from "../components/GeneralInformationForm";

export default function Page() {
	return (
		<div className="container my-5">
			<div className="row justify-content-center">
				<div className="col-12 col-lg-10 col-xl-8">
					<div className="card shadow-sm">
						<div className="card-body p-4 p-md-5">
							<h1 className="h3 mb-4">General Information</h1>
							<GeneralInformationForm />
						</div>
					</div>
				</div>
			</div>
		</div>
	);
}

