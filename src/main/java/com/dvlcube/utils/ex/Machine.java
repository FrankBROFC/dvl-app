package com.dvlcube.utils.ex;

import java.io.File;

import com.dvlcube.utils.MachineUtils;

/**
 * @since 18 de mar de 2019
 * @author Ulisses Lima
 */
public enum Machine implements MachineResource {
	JVM_RAM {
		@Override
		public Long total() {
			return Runtime.getRuntime().maxMemory();
		}

		@Override
		public Long free() {
			long allocatedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
			long presumableFreeMemory = Runtime.getRuntime().maxMemory() - allocatedMemory;
			return presumableFreeMemory;
		}
	}, //
	JVM_LOAD {
		@Override
		public Long total() {
			return MachineUtils.jvmLoad().longValue();
		}

		@Override
		public Long free() {
			return 100 - MachineUtils.jvmLoad().longValue();
		}
	}, //
	DISK {
		@Override
		public Long total() {
			// @broken(ulisses) windows has more than one root
			File root = File.listRoots()[0];
			return root.getTotalSpace();
		}

		@Override
		public Long free() {
			// @broken(ulisses) windows has more than one root
			File root = File.listRoots()[0];
			return root.getFreeSpace();
		}
	}, //
	RAM {
		@Override
		public Long total() {
			// @broken(ulisses) not implemented yet
			return 0l;
		}

		@Override
		public Long free() {
			// @broken(ulisses) not implemented yet
			return 0l;
		}
	}, //
	CORES {
		@Override
		public Long total() {
			return Long.valueOf(Runtime.getRuntime().availableProcessors());

		}

		@Override
		public Long free() {
			return total();
		}
	}, //
	LOAD {
		@Override
		public Long total() {
			return MachineUtils.systemLoad().longValue();
		}

		@Override
		public Long free() {
			return 100 - MachineUtils.systemLoad().longValue();
		}
	}, //
	;

	@Override
	public Long totalMb() {
		return this.total() / 1024 / 1024;
	}

	@Override
	public Long freeMb() {
		return this.free() / 1024 / 1024;
	}

	@Override
	public Double freePercentage() {
		return (double) (free() * 100) / total();
	}

	@Override
	public Double usedPercentage() {
		return 100 - freePercentage();
	}

	public static void main(String[] args) {
		Machine[] machineInfo = Machine.values();
		for (Machine machine : machineInfo) {
			System.out.println(machine.name());
			System.out.println("total: " + machine.total());
			System.out.println("free mb: " + machine.freeMb());
			System.out.println("total mb: " + machine.totalMb());
			System.out.println("free %: " + machine.freePercentage());

			System.out.println();
		}
	}
}