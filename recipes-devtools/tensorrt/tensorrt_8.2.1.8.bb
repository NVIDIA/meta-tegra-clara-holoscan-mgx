# Copyright (c) 2022, NVIDIA CORPORATION. All rights reserved.
#
# Permission is hereby granted, free of charge, to any person obtaining a
# copy of this software and associated documentation files (the "Software"),
# to deal in the Software without restriction, including without limitation
# the rights to use, copy, modify, merge, publish, distribute, sublicense,
# and/or sell copies of the Software, and to permit persons to whom the
# Software is furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in
# all copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
# THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
# FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
# DEALINGS IN THE SOFTWARE.

SUMMARY = "NVIDIA TensorRT"

LICENSE = "CLOSED"

include tensorrt-common.inc

SRC_URI += " \
    file://tensorrt-config.cmake \
"

S = "${WORKDIR}/TensorRT-${PV}"

RDEPENDS:${PN} = " \
    cuda-toolkit \
    cudnn \
"

do_install() {
    install -d ${D}${libdir}
    cp -d --no-preserve=ownership ${S}/targets/${TARGET_ARCH}-${TARGET_OS}-gnu/lib/*.* ${D}${libdir}

    install -d ${D}${includedir}/tensorrt-${PV}
    install -m 0644 ${S}/include/* ${D}${includedir}/tensorrt-${PV}
    ln -s tensorrt-${PV} ${D}${includedir}/tensorrt

    install -d ${D}${libdir}/cmake/tensorrt
    install -m 0644 ${WORKDIR}/tensorrt-config.cmake ${D}${libdir}/cmake/tensorrt
}

do_configure[noexec] = "1"
do_compile[noexec] = "1"

INSANE_SKIP:${PN} += "already-stripped"
