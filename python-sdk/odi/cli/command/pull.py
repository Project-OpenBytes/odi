from odi.client import ODI


def implement_pull(dataset_name, path) -> None:
    odi = ODI()
    odi.pull(dataset=dataset_name, path=path)
